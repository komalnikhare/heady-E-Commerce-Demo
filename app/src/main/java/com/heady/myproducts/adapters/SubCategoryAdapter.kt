package com.heady.myproducts.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heady.myproducts.R
import com.heady.myproducts.activities.ProductActivity
import com.heady.myproducts.dbConfig.models.Category
import kotlinx.android.synthetic.main.adapter_sub_category_view.view.*
import org.jetbrains.anko.startActivity

class SubCategoryAdapter (val context: Context, val categoryList: MutableList<Category>)
    : RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>(){

    override fun getItemCount(): Int {
        return categoryList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return SubCategoryAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_sub_category_view,
                parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(categoryList[position], context)
    }

    class ViewHolder (val view: View) : RecyclerView.ViewHolder(view){

        fun bindItems(category : Category, context: Context){
            view.txt_category_name.text = category.name

            view.txt_category_name.setOnClickListener {

                val intent = Intent(view.context, ProductActivity::class.java)
                intent.putExtra("categoryId", category.id)
                view.context.startActivity(intent)
            }
        }

    }
}