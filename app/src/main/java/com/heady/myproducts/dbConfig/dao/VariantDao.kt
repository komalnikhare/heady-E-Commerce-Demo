package com.heady.myproducts.dbConfig.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.heady.myproducts.dbConfig.models.Variant

@Dao
interface VariantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(variants: List<Variant>)

    @Query("select * from variant where p_id =:productId")
    fun getVariants(productId : Long): LiveData<List<Variant>>

    @Query("delete from variant")
    fun deleteAll()
}