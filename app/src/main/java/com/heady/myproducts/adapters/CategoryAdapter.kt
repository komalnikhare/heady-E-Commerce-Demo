package com.heady.myproducts.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.heady.myproducts.R
import com.heady.myproducts.dbConfig.models.CategoryPojo
import kotlinx.android.synthetic.main.adapter_category_view.view.*
import android.view.Gravity
import android.view.animation.*
import com.heady.myproducts.activities.SubCategoryActivity
import org.jetbrains.anko.custom.style
import org.jetbrains.anko.startActivity


class CategoryAdapter (val context: Context, val categoryList: MutableList<CategoryPojo>)
    : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    override fun getItemCount(): Int {
        return if (categoryList == null) 0 else categoryList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return CategoryAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_category_view,
                parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(categoryList[position])
    }

    class ViewHolder (val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(category : CategoryPojo){
            view.txtCategoryName.text = category.name
            view.childView.visibility = View.GONE
            view.childView.removeAllViews()

            for (subCategory in category.subCategory){
                val textView = TextView(view.context)
                textView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)

                textView.gravity = Gravity.CENTER_VERTICAL
                textView.textSize = 16f
                textView.setTextColor(Color.parseColor("#000000"))
                textView.setPadding(0, 10, 0, 10);
                textView.text = subCategory.name
                textView.setOnClickListener {
                    val intent = Intent(view.context, SubCategoryActivity::class.java )
                    intent.putExtra("category", subCategory.subCategory)
                    view.context.startActivity(intent)

                }

                view.childView.addView(textView)
            }
            view.parentView.setOnClickListener {
                if (view.childView.visibility == View.GONE) {
                    view.childView.visibility = View.VISIBLE
                    view.txtCategoryName.setTypeface( view.txtCategoryName.typeface, Typeface.BOLD);

                    val rotateAnimation = RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
                    rotateAnimation.interpolator = DecelerateInterpolator() as Interpolator?
                    rotateAnimation.repeatCount = 0
                    rotateAnimation.duration = 500
                    rotateAnimation.fillAfter = true
                    view.actionImage.startAnimation(rotateAnimation)

                }else {
                    view.childView.visibility = View.GONE
                    view.txtCategoryName.setTypeface( view.txtCategoryName.typeface, Typeface.NORMAL);

                    val rotateAnimation = RotateAnimation(180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
                    rotateAnimation.interpolator = DecelerateInterpolator()
                    rotateAnimation.repeatCount = 0
                    rotateAnimation.duration = 500
                    rotateAnimation.fillAfter = true
                    view.actionImage.startAnimation(rotateAnimation)
                }
            }
        }
    }
}