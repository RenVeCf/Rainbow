package com.ipd.rainbow.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ExchangeHisBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.platform.http.HttpUrl
import com.ipd.rainbow.ui.activity.coupon.ExchangeRecordDetailActivity
import kotlinx.android.synthetic.main.item_exchange_record.view.*

/**
Created by Miss on 2018/8/13
 */
class ExchangeRecordAdapter(val context: Context, private val data: List<ExchangeHisBean>) : RecyclerView.Adapter<ExchangeRecordAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_exchange_record, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?:0

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        ImageLoader.loadImgFromLocal(context,HttpUrl.IMAGE_URL+data[position].LOGO,holder?.itemView?.iv_exchange_header)
        val category = data[position].CATEGORY
        if (category == 1){
            holder?.itemView?.tv_exchange_title?.text = data[position].PRICE.toString()+"元 单品类优惠券"
        }
        if (category == 2){
            holder?.itemView?.tv_exchange_title?.text = data[position].PRICE.toString()+"元 全品类优惠券"
        }
        holder?.itemView?.tv_exchange_time?.text = "兑换时间："+data[position].CREATETIME
        holder?.itemView?.rl_exchange_record?.setOnClickListener { ExchangeRecordDetailActivity.launch(context as Activity,data[position].EXCHANGE_ID) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}