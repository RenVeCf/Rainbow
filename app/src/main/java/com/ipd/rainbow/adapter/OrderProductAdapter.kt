package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductBean
import com.ipd.rainbow.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_confirm_order_product.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class OrderProductAdapter(val context: Context, private val list: List<ProductBean>?, private val itemClick: (info: ProductBean) -> Unit) : RecyclerView.Adapter<OrderProductAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_confirm_order_product, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_cart_product_img)
        holder.itemView.tv_cart_product_name.text = info.PROCUCT_NAME
        holder.itemView.tv_cart_product_spec.text = info.TASTE
        holder.itemView.tv_cart_product_price.text = "￥" + info.CURRENT_PRICE
        holder.itemView.tv_product_num.text = "数量：x" + info.BUY_NUM

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}