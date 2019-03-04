package com.ipd.rainbow.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.MainActivity
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ExchangeHisBean
import com.ipd.rainbow.ui.activity.store.ProductCategoryActivity
import com.ipd.rainbow.ui.activity.store.ProductDetailActivity
import kotlinx.android.synthetic.main.item_discount_exchange.view.*

/**
Created by Miss on 2018/8/13
 */
class DiscountExchangeAdapter(val context: Context, private val data: List<ExchangeHisBean>) : RecyclerView.Adapter<DiscountExchangeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_discount_exchange, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val info = data[position]
        holder?.itemView?.tv_coupon_money?.text = info.PRICE.toString()
        holder?.itemView?.tv_price?.text = "满" + info.SATISFY_PRICE.toString() + "可用"
        var category = data[position].CATEGORY
        if (category == 1) {
            holder?.itemView?.tv_coupon_title?.text = "单品类优惠券"
        }
        if (category == 2) {
            holder?.itemView?.tv_coupon_title?.text = "全品类优惠券"
        }

        holder?.itemView?.tv_to_use?.setOnClickListener {
            when (category) {
                1 -> {
                    ProductDetailActivity.launch(context as Activity, info.PRODUCT_ID, info.FORM_ID)
                }
                2 -> {
                    MainActivity.launch(context as Activity,"index")
                }
            }
        }

        holder?.itemView?.tv_coupon_validity?.text = "有效期至：" + data[position].END_TIME
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}