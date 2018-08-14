package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.GroupBean
import com.ipd.taxiu.ui.activity.group.GroupDetailActivity
import com.ipd.taxiu.widget.ChooseFriendDialog
import kotlinx.android.synthetic.main.item_group_list.view.*

/**
Created by Miss on 2018/8/13
 */
class GroupListAdapter (val context: Context, private val data: List<GroupBean>): RecyclerView.Adapter<GroupListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_group_list, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?:0

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val status = data[position].status
        when (status) {
            1 -> {
                holder?.itemView?.commodity_status?.text = "待成团"
                holder?.itemView?.order_number?.text = "5人团，还差2人"
                holder?.itemView?.tv_confirm?.text = "邀请好友"
                holder?.itemView?.tv_confirm?.setOnClickListener{
                    val dialog = ChooseFriendDialog(context, R.style.recharge_pay_dialog, 2)
                    dialog.show()
                }
                holder?.itemView?.tv_cancel?.setOnClickListener{ startActivity(1) }
                holder?.itemView?.tv_cancel?.visibility = View.VISIBLE
                holder?.itemView?.tv_confirm?.setBackgroundResource(R.drawable.shape_order_btn_confirm)
                holder?.itemView?.tv_confirm?.setTextColor(context.resources.getColor(R.color.colorPrimaryDark))
            }
            2 -> {
                holder?.itemView?.commodity_status?.text = "已成团"
                holder?.itemView?.order_number?.text = "5人团，还差0人"
                holder?.itemView?.tv_confirm?.text = "详情"

                holder?.itemView?.tv_confirm?.setOnClickListener{ startActivity(2) }

                holder?.itemView?.tv_cancel?.visibility = View.GONE
                holder?.itemView?.tv_confirm?.setBackgroundResource(R.drawable.shape_order_btn_cancel)
                holder?.itemView?.tv_confirm?.setTextColor(context.resources.getColor(R.color.black))
            }
            3 -> {
                holder?.itemView?.commodity_status?.text = "未成团"
                holder?.itemView?.order_number?.text = "5人团，参团人数不足"
                holder?.itemView?.tv_confirm?.text = "详情"
                holder?.itemView?.tv_confirm?.setOnClickListener{ startActivity(3) }
                holder?.itemView?.tv_cancel?.visibility = View.GONE
                holder?.itemView?.tv_confirm?.setBackgroundResource(R.drawable.shape_order_btn_cancel)
                holder?.itemView?.tv_confirm?.setTextColor(context.resources.getColor(R.color.black))
            }
        }
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private fun startActivity(GroupStatus: Int) {
        GroupDetailActivity.launch(context as Activity, GroupStatus)
    }
}