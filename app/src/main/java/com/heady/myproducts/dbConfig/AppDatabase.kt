package com.heady.myproducts.dbConfig

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.heady.myproducts.dbConfig.dao.CategoryDao
import com.heady.myproducts.dbConfig.dao.ProductDao
import com.heady.myproducts.dbConfig.dao.RankingDao
import com.heady.myproducts.dbConfig.dao.VariantDao
import com.heady.myproducts.dbConfig.models.Category
import com.heady.myproducts.dbConfig.models.Product
import com.heady.myproducts.dbConfig.models.Ranking
import com.heady.myproducts.dbConfig.models.Variant
import java.util.*

@Database(entities = arrayOf(Category::class, Ranking::class, Product::class, Variant::class),
        version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase(){

    abstract fun categoryDao():CategoryDao
    abstract fun rankingDao():RankingDao
    abstract fun productDao():ProductDao
    abstract fun variantDao():VariantDao



    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
                INSTANCE ?: synchronized(this){
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it}
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "myproduct.db")
                        .build()
    }
}