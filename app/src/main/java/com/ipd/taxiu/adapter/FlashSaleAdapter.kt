package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ProductBean
import kotlinx.android.synthetic.main.item_flash_sale_header.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class FlashSaleAdapter(val context: Context, private val list: List<ProductBean>?, private val itemClick: (info: ProductBean) -> Unit,val tabListener: (pos: Int) -> Unit) : RecyclerView.Adapter<FlashSaleAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0

    object ItemType {
        const val HEADER = 0
        const val CONTENT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) ItemType.HEADER else ItemType.CONTENT
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.HEADER -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_flash_sale_header, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_flash_sale_product, parent, false))
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        when (getItemViewType(position)) {
            ItemType.HEADER -> {
                holder.itemView.tab_layout.setTabListener {
                    tabListener?.invoke(it)
                }

            }
            ItemType.CONTENT -> {
                holder.itemView.setOnClickListener { itemClick.invoke(info) }

            }
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}