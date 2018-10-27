package com.heady.myproducts.dbConfig.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "product",
        foreignKeys = [
            ForeignKey(entity = Category::class,
                    parentColumns = ["id"],
                    childColumns = ["c_id"])])
class Product (@PrimaryKey(autoGenerate = false) var id : Long,
               @ColumnInfo(name = "c_id") var c_id: Long,
               @ColumnInfo(name = "name") var name: String,
               @ColumnInfo(name = "date_added") var date_added: String,
               @ColumnInfo(name = "tax_name") var tax_name: String,
               @ColumnInfo(name = "tax_value") var tax_value: Double,
               @ColumnInfo(name = "view_count") var view_count: Int?,
               @ColumnInfo(name = "order_count") var order_count: Int?,
               @ColumnInfo(name = "share_count") var share_count: Int?)