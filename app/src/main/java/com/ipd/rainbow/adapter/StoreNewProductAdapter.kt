package com.ipd.rainbow.adapter

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.utils.StringUtils
import kotlinx.android.synthetic.main.item_special_product.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class StoreNewProductAdapter(val context: Context, private val list: List<ProductBean>?, private val itemClick: (info: ProductBean) -> Unit) : RecyclerView.Adapter<StoreNewProductAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_special_product, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_product_img)
        holder.itemView.tv_product_name.text = info.NAME
        holder.itemView.tv_product_price.text = StringUtils.getEncryPrice(true, info.CURRENT_PRICE, info.KIND)


        holder.itemView.tv_old_product_price.text = "￥${info.PRICE}"
        holder.itemView.tv_old_product_price.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG


        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}