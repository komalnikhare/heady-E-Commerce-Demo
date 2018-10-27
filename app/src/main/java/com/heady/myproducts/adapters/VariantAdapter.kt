package com.heady.myproducts.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heady.myproducts.R
import com.heady.myproducts.dbConfig.models.Variant
import kotlinx.android.synthetic.main.adapter_variant_view.view.*

class VariantAdapter(val context: Context, val variantList: MutableList<Variant>, val tax: Double)
    : RecyclerView.Adapter<VariantAdapter.ViewHolder>(){

    override fun getItemCount(): Int {
        return if (variantList == null) 0 else variantList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_variant_view,
                parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val variant = variantList[position]

        holder.price.text = "${holder.price.text.toString()} ${variant.price+tax} \n Including all taxes"
        holder.size.text = "${holder.size.text.toString()} ${variant.size}"
        holder.color.text = "${holder.color.text.toString()} ${variant.color}"

    }


    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){

        val price = view.txt_Price
        val color = view.txt_color
        val size  = view.txt_Size
    }
}

