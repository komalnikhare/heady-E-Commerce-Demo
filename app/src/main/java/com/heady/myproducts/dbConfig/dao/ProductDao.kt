package com.heady.myproducts.dbConfig.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.heady.myproducts.dbConfig.models.Product
import io.reactivex.Flowable

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Query("select * from product where id =:id")
    fun getProduct(id: Long) : Product

    @Query("select * from product where c_id =:categoryId")
    fun getProducts(categoryId : Long) : LiveData<List<Product>>

    @Query("select * from product where c_id = :categoryId order by view_count DESC")
    fun getMostViewedProduct(categoryId: Long): LiveData<List<Product>>

    @Query("select * from product where c_id = :categoryId order by order_count DESC")
    fun getMostOrderedProduct(categoryId: Long): LiveData<List<Product>>

    @Query("select * from product where c_id = :categoryId order by share_count DESC")
    fun getMostSharedProduct(categoryId: Long): LiveData<List<Product>>

    @Query("delete from product")
    fun deleteAll()

}