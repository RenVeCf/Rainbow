package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.BalanceBillBean
import kotlinx.android.synthetic.main.item_balance_bill.view.*

/**
Created by Miss on 2018/8/13
 */
class BalanceBillAdapter(val context: Context, private val data: List<BalanceBillBean>?) : RecyclerView.Adapter<BalanceBillAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_balance_bill, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = data!![position]
        holder.itemView.integral_title.text = info.TITLE
        holder.itemView.integral_explain.text = info.CONTENT
        holder.itemView.integral_time.text = info.CREATETIME

        if (info.CATEGORY == 1 || info.CATEGORY == 3) {
            holder.itemView.trading_status.visibility = View.VISIBLE
            holder.itemView.trading_status.text =
                    when (info.STATUS) {
                        1 -> "审核中"
                        2 -> "审核失败"
                        3 -> "已完成"
                        else -> ""
                    }
        } else {
            holder.itemView.trading_status.visibility = View.GONE
        }

        holder.itemView.integral_money.text = info.MONEY
        if (!TextUtils.isEmpty(info.MONEY) && !info.MONEY.contains("-")) {
            holder.itemView.integral_money.setTextColor(context.resources.getColor(R.color.earning_text))
        } else {
            holder.itemView.integral_money.setTextColor(context.resources.getColor(R.color.black))
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}