package com.heady.myproducts.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.heady.myproducts.dbConfig.dao.VariantDao
import com.heady.myproducts.dbConfig.models.Variant
import io.reactivex.Completable

class VariantViewModel(private val variantDao: VariantDao) : ViewModel(){

    fun insertAll(variants: List<Variant>) {
        variantDao.insertAll(variants)
    }

    fun getVariants(productId : Long): LiveData<List<Variant>>{
        return variantDao.getVariants(productId)
    }

}