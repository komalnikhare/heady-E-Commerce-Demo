package com.heady.myproducts.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.heady.myproducts.R
import com.heady.myproducts.adapters.CategoryAdapter
import com.heady.myproducts.dbConfig.models.*
import com.heady.myproducts.services.VolleyService
import com.heady.myproducts.viewModels.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONException
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {

    private val url = "https://stark-spire-93433.herokuapp.com/json"

    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var productViewModel: ProductViewModel
    private lateinit var rankingViewModel: RankingViewModel
    private lateinit var variantViewModel: VariantViewModel

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModelFactory = Injection.getViewModelFactory(this)
        categoryViewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoryViewModel::class.java)
        productViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductViewModel::class.java)
        rankingViewModel = ViewModelProviders.of(this, viewModelFactory).get(RankingViewModel::class.java)
        variantViewModel = ViewModelProviders.of(this, viewModelFactory).get(VariantViewModel::class.java)

        getProduct()
    }

    override fun onResume() {
        super.onResume()

        categoryViewModel.getMasterCategory().observe(this,
                Observer<List<Category>> { categoryList ->
                    if (categoryList != null) {
                        sortCategory(categoryList.toMutableList())
                    }

                })
    }

    private fun sortCategory(categories: MutableList<Category>) {
        val categoryList: MutableList<CategoryPojo> = mutableListOf()

        for (i in 0 until (categories.size -1)) {
            if (i == categories.size)
                break
            val category = categories.get(i)
            val subCategoryList: MutableList<SubCategoryPojo> = mutableListOf()

            val child = category.child_categories?.split(",".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
            for (str in child!!) {
                val result = categories.any { c -> c.id == str.toLongOrNull() }
                if (result) {
                    val ct : Category? = categories.find { it.id == str.toLongOrNull()}
                    val subCategoryPojo = SubCategoryPojo(ct!!.id, ct.name, ct.product_count, ct.child_categories)
                    subCategoryList.add(subCategoryPojo)
                    categories.remove(ct)
                }
            }
            val cat = CategoryPojo(category.id, category.name, category.product_count, category.child_categories, subCategoryList)
            categoryList.add(cat)
        }

        categoryList.size;
        val adapter = CategoryAdapter(this, categoryList)
        categoryRecyclerView.adapter = adapter
    }


    /**
     * Service call to get data and store into local db
     */
    private fun getProduct() {
        val request = JsonObjectRequest(Request.Method.GET, url,
                Response.Listener<JSONObject> { response ->

                    saveData(response)

                },
                Response.ErrorListener {
                    val error = it as VolleyError
                })
        VolleyService.requestQueue.add(request)
        VolleyService.requestQueue.start()
    }

    private fun saveData(response: JSONObject) {
        val categories = response
                .optJSONArray("categories")
        val ranking = response.optJSONArray("rankings")

        val categoryList: MutableList<Category> = mutableListOf()
        var productList: MutableList<Product> = mutableListOf()
        var variantList: MutableList<Variant> = mutableListOf<Variant>()

        var viewedMap: MutableMap<Int, Int> = mutableMapOf()
        var orderedMap: MutableMap<Int, Int> = mutableMapOf()
        var sharedMap: MutableMap<Int, Int> = mutableMapOf()

        for (i in 0 until categories.length()) {
            try {
                val obj = categories.getJSONObject(i)
                val id = obj.getInt("id")
                val name = obj.getString("name")
                val productArray = obj.getJSONArray("products")
                val productCount = productArray.length()
                val subCategory: String?
                val sbCArray = obj.getJSONArray("child_categories")
                if (sbCArray.length() > 0) {
                    var sb = StringBuilder()

                    for (j in 0 until sbCArray.length()) {
                        sb.append(sbCArray[j]).append(",")
                    }
                    subCategory = sb.deleteCharAt(sb.length - 1).toString()
                } else {
                    subCategory = null
                }

                val category = Category(id.toLong(), name, productCount, subCategory)
                categoryList.add(category)
                /* disposable.add(categoryViewModel.insertCategory(category)
                         .subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread())
                         .subscribe())*/

                for (j in 0 until productArray.length()) {
                    val obj = productArray.getJSONObject(j)
                    val product = Product(obj.getInt("id").toLong(),
                            id.toLong(), obj.getString("name"),
                            obj.getString("date_added"),
                            obj.getJSONObject("tax").getString("name"),
                            obj.getJSONObject("tax").getDouble("value"),
                            0, 0, 0)

                    productList.add(product)

                    /*disposable.add(productViewModel.insert(product)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe())*/

                    var variants = obj.getJSONArray("variants")
                    for (k in 0 until variants.length()) {
                        val cObj = variants.getJSONObject(k)
                        val variant = Variant(cObj.getInt("id").toLong(),
                                obj.getInt("id").toLong(), cObj.getString("color"),
                                cObj.optInt("size", 0), cObj.getInt("price").toDouble())
                        variantList.add(variant)
                    }
                    /*disposable.add(variantViewModel.insertAll(variantList)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe())*/

                }

            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
        for (i in 0 until ranking.length()) {

            val obj = ranking.getJSONObject(i)
            val string = obj.getString("ranking")
            val ranking = Ranking((i + 1).toLong(), string)
            disposable.add(rankingViewModel.insert(ranking)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe())

            val products = obj.getJSONArray("products")

            for (j in 0 until products.length()) {
                val o = products.getJSONObject(j)
                val id = o.getInt("id");
                var value: Int = 0
                if (string.toLowerCase().contains("viewed")) {
                    value = o.optInt("view_count", 0)
                    viewedMap.set(id, value)
                } else if (string.toLowerCase().contains("ordered")) {
                    value = o.optInt("order_count", 0)
                    orderedMap.set(id, value)
                } else if (string.toLowerCase().contains("shared")) {
                    value = o.optInt("shares", 0)
                    sharedMap.set(id, value)
                }

            }
        }

        for (product in productList) {
            if (viewedMap.containsKey(product.id.toInt())) {
                product.view_count = viewedMap.getValue(product.id.toInt())
            }
            if (orderedMap.containsKey(product.id.toInt())) {
                product.order_count = orderedMap.getValue(product.id.toInt())
            }
            if (sharedMap.containsKey(product.id.toInt())) {
                product.share_count = sharedMap.getValue(product.id.toInt())
            }
        }
        Observable.fromCallable {
            categoryViewModel.insertCategories(categoryList)
            productViewModel.insertAll(productList)
            variantViewModel.insertAll(variantList)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

        //val task = Task(viewedMap,orderedMap,sharedMap,productViewModel).execute()

    }

}
