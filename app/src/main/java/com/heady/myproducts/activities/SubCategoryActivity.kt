package com.heady.myproducts.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import com.heady.myproducts.R
import com.heady.myproducts.adapters.SubCategoryAdapter
import com.heady.myproducts.dbConfig.models.Category
import com.heady.myproducts.viewModels.CategoryViewModel
import com.heady.myproducts.viewModels.Injection
import com.heady.myproducts.viewModels.ViewModelFactory
import kotlinx.android.synthetic.main.activity_sub_category.*

class SubCategoryActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var categoryViewModel: CategoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        val category = intent.getStringExtra("category")

        val catArray = category.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val categoryId = catArray.map { it.toLong() }.toTypedArray()

        viewModelFactory = Injection.getViewModelFactory(this)
        categoryViewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoryViewModel::class.java)

        getCategories(categoryId)
    }

    private fun getCategories(categoryIds : Array<Long>){
        categoryViewModel.getCategoryList(categoryIds).observe(this,
                Observer<List<Category>> {categoryList ->
                    if (categoryList!= null){
                        val adapter  = SubCategoryAdapter(this, categoryList.toMutableList())
                        subCategoryRecyclerView.adapter = adapter
                    }
                })
    }
}
