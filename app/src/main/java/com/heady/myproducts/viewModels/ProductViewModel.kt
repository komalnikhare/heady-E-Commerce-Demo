package com.heady.myproducts.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.heady.myproducts.dbConfig.dao.ProductDao
import com.heady.myproducts.dbConfig.models.Product
import io.reactivex.Completable

class ProductViewModel(private val productDao: ProductDao) : ViewModel() {
    fun insertAll(products : List<Product>) {
            productDao.insertAll(products)
    }

    fun insert(product : Product)  {
            productDao.insert(product)
    }


    fun getProducts(categoryId : Long): LiveData<List<Product>>{
        return productDao.getProducts(categoryId)
    }

    fun getMostViewedProduct(categoryId: Long): LiveData<List<Product>>{
        return productDao.getMostViewedProduct(categoryId)
    }

    fun getMostOrderedProduct(categoryId: Long): LiveData<List<Product>>{
        return productDao.getMostOrderedProduct(categoryId)
    }

    fun getMostSharedProduct(categoryId: Long): LiveData<List<Product>>{
        return productDao.getMostSharedProduct(categoryId)
    }

}