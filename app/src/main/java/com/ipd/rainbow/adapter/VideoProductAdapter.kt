package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.utils.StoreType
import kotlinx.android.synthetic.main.item_product.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class VideoProductAdapter(val context: Context, private val list: List<ProductBean>?, private val itemClick: (info: ProductBean) -> Unit) : RecyclerView.Adapter<VideoProductAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        holder.itemView.iv_new_product.visibility = if (info.isNew) View.VISIBLE else View.GONE

        ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_product_img)
        holder.itemView.tv_product_name.text = info.NAME
        holder.itemView.tv_product_price.text = "￥${info.CURRENT_PRICE}"
        holder.itemView.tv_product_price_old.text = "￥${info.PRICE}"
        holder.itemView.tv_product_price_old.visibility = if (info.KIND == StoreType.PRODUCT_NORMAL) View.GONE else View.VISIBLE
        holder.itemView.tv_product_evalute.text = "评价 ${info.ASSESS}"
        holder.itemView.tv_product_sales.text = "销量 ${info.SALE}"
        holder.itemView.setOnClickListener { itemClick.invoke(info) }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}