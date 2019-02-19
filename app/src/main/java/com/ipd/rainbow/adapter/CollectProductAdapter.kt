package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductBean
import com.ipd.rainbow.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_product.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class CollectProductAdapter(val context: Context, private val list: List<ProductBean>?, private val itemClick: (info: ProductBean) -> Unit) : RecyclerView.Adapter<CollectProductAdapter.ViewHolder>() {

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
                holder.itemView.iv_new_product.visibility = if (info.isNew) View.VISIBLE else View.GONE

                ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_product_img)
                holder.itemView.tv_product_name.text = info.NAME
                holder.itemView.tv_product_price.text = "￥${info.CURRENT_PRICE}"
                holder.itemView.tv_product_price_old.text = "￥${info.PRICE}"
                holder.itemView.tv_product_evalute.text = "评价 ${info.ASSESS}"
                holder.itemView.tv_product_sales.text = "销量 ${info.SALE}"

                holder.itemView.tv_product_lable.visibility = if (info.KIND == 1) View.GONE else View.VISIBLE
                holder.itemView.tv_product_lable.text = info.kindStr

            }
            ItemType.GRID -> {
                ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_product_img)
                holder.itemView.tv_product_name.text = info.NAME
                holder.itemView.tv_product_price.text = "￥${info.CURRENT_PRICE}"
                holder.itemView.tv_product_evalute.text = "评价 ${info.ASSESS}"
                holder.itemView.tv_product_sales.text = "销量 ${info.SALE}"

            }
        }
        holder.itemView.setOnClickListener { itemClick.invoke(info) }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}