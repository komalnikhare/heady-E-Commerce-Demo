package com.heady.myproducts.viewModels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.heady.myproducts.dbConfig.AppDatabase
import java.lang.IllegalArgumentException

class ViewModelFactory(private val appDatabase: AppDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)){
            return CategoryViewModel(appDatabase.categoryDao()) as T
        }else if (modelClass.isAssignableFrom(ProductViewModel::class.java)){
            return ProductViewModel(appDatabase.productDao()) as T
        }else if(modelClass.isAssignableFrom(VariantViewModel::class.java)){
            return VariantViewModel(appDatabase.variantDao()) as T
        }else if (modelClass.isAssignableFrom(RankingViewModel::class.java)){
            return  RankingViewModel(appDatabase.rankingDao()) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}