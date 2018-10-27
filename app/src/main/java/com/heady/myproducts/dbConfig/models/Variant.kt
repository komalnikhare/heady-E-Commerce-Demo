package com.heady.myproducts.dbConfig.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Variant",
        foreignKeys = [
            ForeignKey(entity = Product::class,
                    parentColumns = ["id"],
                    childColumns = ["p_id"])])
class Variant (@PrimaryKey(autoGenerate = false) var id : Long,
               @ColumnInfo(name = "p_id") var p_id: Long,
               @ColumnInfo(name = "color") var color: String,
               @ColumnInfo(name = "size") var size: Int,
               @ColumnInfo(name = "price") var price: Double)