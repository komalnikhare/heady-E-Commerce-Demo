package com.heady.myproducts.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.heady.myproducts.dbConfig.dao.CategoryDao
import com.heady.myproducts.dbConfig.models.Category
import io.reactivex.Completable
import io.reactivex.Flowable

class CategoryViewModel(private val categoryDao: CategoryDao) : ViewModel() {


    fun insertCategory(category: Category ){
            categoryDao.insert(category)
    }

    fun insertCategories(categories : List<Category>) {
            categoryDao.insertAll(categories)
    }

    fun getMasterCategory(): LiveData<List<Category>> {
        return categoryDao.getMasterCategory()
    }
    fun getProductCount(id: Long): Flowable<Int>{
        return categoryDao.getProductCount(id)
    }

    fun getCategoryList(categoryIds : Array<Long>): LiveData<List<Category>>{
        return categoryDao.getCategoryList(categoryIds)
    }
}