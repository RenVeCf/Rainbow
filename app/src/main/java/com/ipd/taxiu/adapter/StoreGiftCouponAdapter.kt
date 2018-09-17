package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ExchangeHisBean
import kotlinx.android.synthetic.main.item_store_gift_coupon.view.*

/**
Created by Miss on 2018/8/13
 */
class StoreGiftCouponAdapter(val context: Context, private val data: List<ExchangeHisBean>) : RecyclerView.Adapter<StoreGiftCouponAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_store_gift_coupon, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = data[position]

        holder.itemView.tv_coupon_money.text = info.SATISFY_PRICE.toString()
        holder.itemView.tv_coupon_rule.text = "满" + info.PRICE.toString() + "可用"
        when (info.CATEGORY) {
            1 -> holder.itemView.tv_coupon_kind.text = "单品类优惠券"
            2 -> holder.itemView.tv_coupon_kind.text = "单品类优惠券"

        }
        holder.itemView.tv_coupon_rule.text = "满" + info.PRICE.toString() + "可用"
        holder.itemView.tv_coupon_date.text = String.format(context.getString(R.string.store_gift_coupon_date), info.END_TIME)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}