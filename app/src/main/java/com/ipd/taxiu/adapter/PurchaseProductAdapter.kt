package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.PurchaseProductBean
import com.ipd.taxiu.utils.StringUtils
import kotlinx.android.synthetic.main.item_group_purchase.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class PurchaseProductAdapter(val context: Context, private val list: List<PurchaseProductBean>?, private val itemClick: (info: PurchaseProductBean) -> Unit) : RecyclerView.Adapter<PurchaseProductAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_group_purchase, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        val surplusTime = info.endTime - System.currentTimeMillis()
        StringUtils.getCountDownByTime(surplusTime,{hours, minutes, second ->
            holder.itemView.tv_group_purchase_hours.text = hours
            holder.itemView.tv_group_purchase_minute.text = minutes
            holder.itemView.tv_group_purchase_second.text = second
        })


        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}