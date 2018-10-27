package com.heady.myproducts.dbConfig.dao

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.heady.myproducts.dbConfig.models.Category
import io.reactivex.Flowable

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories : List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category)

    @Query("select * from category where product_count = 0")
    fun getMasterCategory(): LiveData<List<Category>>

    @Query("select product_count from category where id = :id")
    fun getProductCount(id: Long): Flowable<Int>

    @Query("select * from category where id in (:categoryIds)")
    fun getCategoryList(categoryIds : Array<Long>): LiveData<List<Category>>


    @Query("delete from category")
    fun deleteAll()
}