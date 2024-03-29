package com.ipd.rainbow.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ExchangeBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.platform.http.HttpUrl
import com.ipd.rainbow.ui.activity.coupon.CouponDetailActivity
import kotlinx.android.synthetic.main.item_integral_exchange.view.*

/**
Created by Miss on 2018/8/13
 */
class IntegralExchangeAdapter(val context: Context, private val data: List<ExchangeBean>) : RecyclerView.Adapter<IntegralExchangeAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_integral_exchange, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?:0

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val info = data[position]
        holder?.itemView?.tv_integral_num?.text = info.SCORE.toString()
        var category:Int = info.CATEGORY
        if (category == 1) {
            holder?.itemView?.tv_discount_coupon_name?.text = info.PRICE.toString() + "元 单品类优惠券"
        }
        if (category == 2) {
            holder?.itemView?.tv_discount_coupon_name?.text = info.PRICE.toString() + "元 全品类优惠券"
        }
        ImageLoader.loadImgFromLocal(context,HttpUrl.IMAGE_URL+info.LOGO, holder?.itemView?.iv_discount_coupon)

        holder?.itemView?.setOnClickListener { CouponDetailActivity.launch(context as Activity,info.COUPON_ID) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}