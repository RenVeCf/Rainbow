package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ProductCategoryBean
import com.ipd.taxiu.bean.ProductCategoryTitleBean
import kotlinx.android.synthetic.main.item_product_category_title.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class ProductCategoryAdapter(val context: Context, private val list: List<Any>?, val itemClick: (info: ProductCategoryBean) -> Unit) : RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0

    object ItemType {
        const val TITLE = 0
        const val CONTENT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (list!![position] is ProductCategoryTitleBean) ItemType.TITLE else ItemType.CONTENT
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.TITLE -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_category_title, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_category, parent, false))
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.TITLE -> {
                val info = list!![position] as ProductCategoryTitleBean
                holder.itemView.tv_category_title.text = info.titleName

            }
            ItemType.CONTENT -> {
                val info = list!![position] as ProductCategoryBean
                holder.itemView.setOnClickListener { itemClick.invoke(info) }

            }
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}