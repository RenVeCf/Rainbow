package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ExchangeBean
import com.ipd.taxiu.ui.activity.coupon.CouponDetailActivity

/**
Created by Miss on 2018/8/13
 */
class IntegralExchangeAdapter(val context: Context, private val data: List<ExchangeBean>) : RecyclerView.Adapter<IntegralExchangeAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_integral_exchange, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?:0

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.itemView?.setOnClickListener { CouponDetailActivity.launch(context as Activity) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}