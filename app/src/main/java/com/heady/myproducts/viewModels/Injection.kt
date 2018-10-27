package com.heady.myproducts.viewModels

import android.content.Context
import com.heady.myproducts.dbConfig.AppDatabase

object Injection {

    fun getDatabase(context: Context): AppDatabase{
       return AppDatabase.getInstance(context)
    }

    fun getViewModelFactory(context: Context) : ViewModelFactory{
        return ViewModelFactory(getDatabase(context))
    }
}