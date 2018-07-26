package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ProductBean

/**
 * Created by jumpbox on 2017/8/31.
 */
class ProductAdapter(val context: Context, private val list: List<ProductBean>?, private val itemClick: (info: ProductBean) -> Unit) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0

    object ItemType {
        const val LIST = 0
        const val GRID = 1
    }

    var mType: Int = ItemType.LIST

    fun switchShowType(): Int {
        mType = if (mType == ItemType.LIST) ItemType.GRID else ItemType.LIST
        return mType
    }
    override fun getItemViewType(position: Int): Int {
        return mType
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.LIST -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_grid, parent, false))
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        when (getItemViewType(position)) {
            ItemType.LIST -> {

            }
            ItemType.GRID -> {

            }
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}