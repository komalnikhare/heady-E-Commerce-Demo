package com.heady.myproducts.dbConfig.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "ranking")
class Ranking(@PrimaryKey(autoGenerate = false) var id: Long,
              @ColumnInfo(name = "ranking") var ranking: String)