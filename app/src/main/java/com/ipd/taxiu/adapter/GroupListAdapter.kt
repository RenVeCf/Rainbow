package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.GroupBean
import kotlinx.android.synthetic.main.item_group_list.view.*

/**
Created by Miss on 2018/8/13
 */
class GroupListAdapter(val context: Context, private val data: List<GroupBean>?, private val itemClick: (info: GroupBean) -> Unit) : RecyclerView.Adapter<GroupListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_group_list, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = data!![position]

        holder.itemView.order_number.text = "${info.TEAM_NUM}人团，还差${info.JOIN_NUM}人成团"
        holder.itemView.commodity_status.text = when (info.TEAM_STATUS) {
            2 -> "已成团"
            3 -> "未成团"
            else -> "待成团"
        }


        holder.itemView.tv_commodity_pay.text = "￥${info.PAY_FEE}"

        holder.itemView.tv_confirm.visibility = if (info.TEAM_STATUS == 1) View.VISIBLE else View.GONE

        holder.itemView.product_recycler_view.adapter = OrderProductAdapter(context, info.PRODUCT_LIST, {
            itemClick?.invoke(info)
        })

        holder.itemView.setOnClickListener {
            itemClick?.invoke(info)
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}