package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.RecommendEarningsBean
import com.ipd.taxiu.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_earning_detail.view.*

/**
Created by Miss on 2018/8/13
推荐收益明细adapter
 */
class EarningDetailAdapter(val context: Context, private val data: List<RecommendEarningsBean>?) : RecyclerView.Adapter<EarningDetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_earning_detail, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = data!![position]
        ImageLoader.loadAvatar(context, info.LOGO, holder.itemView.civ_friend_header)
        holder.itemView.tv_friend_nickname.text = info.NICKNAME
        holder.itemView.tv_earning_time.text = info.CREATETIME
        holder.itemView.tv_earning_score.text = "+" + info.SCORE


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}