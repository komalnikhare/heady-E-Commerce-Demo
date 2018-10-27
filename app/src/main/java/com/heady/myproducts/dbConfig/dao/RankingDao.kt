package com.heady.myproducts.dbConfig.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.heady.myproducts.dbConfig.models.Ranking

@Dao
interface RankingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ranking: Ranking)

    @Query("delete from ranking")
    fun deleteAll()
}