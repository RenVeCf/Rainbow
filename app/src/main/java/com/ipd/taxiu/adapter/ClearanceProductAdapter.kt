package com.ipd.taxiu.adapter

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_clearance_product.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class ClearanceProductAdapter(val context: Context, private val mType: Int, private val list: List<ProductBean>?, private val itemClick: (info: ProductBean) -> Unit) : RecyclerView.Adapter<ClearanceProductAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_clearance_product, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        holder.itemView.tv_product_price_old.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG

        ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_product_img)
        holder.itemView.tv_cart_product_name.text = info.PROCUCT_NAME
        holder.itemView.tv_price.text = info.CURRENT_PRICE
        holder.itemView.tv_product_price_old.text = "￥${info.REFER_PRICE}"
        //临期清仓显示有效期
        holder.itemView.tv_product_sales.text = if (mType == 2) "有效期至 ${info.END_TIME}" else "库存 ${info.STOCK}"

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}