package com.heady.myproducts.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import com.heady.myproducts.R
import com.heady.myproducts.adapters.ProductAdapter
import com.heady.myproducts.dbConfig.models.Product
import com.heady.myproducts.viewModels.Injection
import com.heady.myproducts.viewModels.ProductViewModel
import com.heady.myproducts.viewModels.ViewModelFactory
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var productViewModel: ProductViewModel

    private var categoryId : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

         categoryId = intent.getLongExtra("categoryId", 0)

        viewModelFactory = Injection.getViewModelFactory(this)
        productViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductViewModel::class.java)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radioViewed ->
                    sortProductByMostViewed(categoryId)
                R.id.radioOrdered ->
                    sortProductByMostOrdered(categoryId)
                R.id.radioShared ->
                    sortProductByMostShared(categoryId)
            }
        }
        getProducts(categoryId)
    }

    private fun getProducts(categoryId : Long){
        productViewModel.getProducts(categoryId).observe(this,
                Observer<List<Product>>{productList ->
                    if (productList != null){
                        val adapter = ProductAdapter(this, productList.toMutableList())
                        productRecyclerView.adapter = adapter
                    }
                })
    }

    private fun sortProductByMostViewed(categoryId: Long){
        productViewModel.getMostViewedProduct(categoryId).observe(this,
                Observer<List<Product>>{productList ->
                    if (productList != null){
                        val adapter = ProductAdapter(this, productList.toMutableList())
                        productRecyclerView.adapter = adapter
                        productRecyclerView.invalidate()
                    }
                })
    }

    private fun sortProductByMostOrdered(categoryId: Long){
        productViewModel.getMostOrderedProduct(categoryId).observe(this,
                Observer<List<Product>>{productList ->
                    if (productList != null){
                        val adapter = ProductAdapter(this, productList.toMutableList())
                        productRecyclerView.adapter = adapter
                        productRecyclerView.invalidate()
                    }
                })
    }

    private fun sortProductByMostShared(categoryId: Long){
        productViewModel.getMostSharedProduct(categoryId).observe(this,
                Observer<List<Product>>{productList ->
                    if (productList != null){
                        val adapter = ProductAdapter(this, productList.toMutableList())
                        productRecyclerView.adapter = adapter
                        productRecyclerView.invalidate()
                    }
                })
    }
}
