package com.heady.myproducts.dbConfig.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "category")
class Category (@PrimaryKey(autoGenerate = false) var id : Long,
                @ColumnInfo(name = "name") var name: String,
                @ColumnInfo(name = "product_count") var product_count: Int,
                @ColumnInfo(name = "child_categories") var child_categories: String?)