package com.heady.myproducts.adapters

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.heady.myproducts.R
import com.heady.myproducts.dbConfig.models.Product
import com.heady.myproducts.dbConfig.models.Variant
import com.heady.myproducts.viewModels.Injection
import com.heady.myproducts.viewModels.ProductViewModel
import com.heady.myproducts.viewModels.VariantViewModel
import kotlinx.android.synthetic.main.adapter_product_view.view.*

class ProductAdapter (val context: Context, val productList: MutableList<Product>)
    : RecyclerView.Adapter<ProductAdapter.ViewHolder>(){

    override fun getItemCount(): Int {
        return if (productList == null) 0 else productList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_product_view,
                parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(productList[position], context)
    }

    class ViewHolder (val view: View) : RecyclerView.ViewHolder(view){

        fun bindItems(product: Product, context: Context){
            view.txt_name.text = product.name

            val viewModelFactory = Injection.getViewModelFactory(view.context as AppCompatActivity)
            val variantViewModel = ViewModelProviders.of(
                    view.context as AppCompatActivity, viewModelFactory).get(VariantViewModel::class.java)

            variantViewModel.getVariants(product.id).observe(view.context as AppCompatActivity,
                    Observer <List<Variant>>{variantList ->
                        if (variantList!=null){
                            val adapter = VariantAdapter(view.context, variantList.toMutableList(), product.tax_value )
                            view.variantList.adapter = adapter
                        }
                    })
        }

    }
}