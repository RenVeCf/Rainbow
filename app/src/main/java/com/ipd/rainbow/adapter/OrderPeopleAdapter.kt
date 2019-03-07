package com.ipd.rainbow.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.OrderPeopleBean
import com.ipd.rainbow.ui.activity.address.AddAddressActivity
import com.ipd.rainbow.ui.activity.address.AddOrderPeopleActivity
import kotlinx.android.synthetic.main.item_order_people.view.*

/**
Created by Miss on 2018/8/13
订购人adapter
 */
class OrderPeopleAdapter(val context: Context, private val data: List<OrderPeopleBean>, private val itemClick: (info: OrderPeopleBean) -> Unit) : RecyclerView.Adapter<OrderPeopleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_people, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (data[position].STATUS == 2) {
            holder?.itemView?.tv_default?.visibility = View.VISIBLE
        } else {
            holder?.itemView?.tv_default?.visibility = View.GONE
        }
        holder?.itemView?.setOnClickListener {
            itemClick.invoke(data[position])
        }
        holder?.itemView?.iv_edit_address?.setOnClickListener {
            AddOrderPeopleActivity.launch(context as Activity, 2, data[position].SUBSCRIBER_ID)
        }

        holder?.itemView?.tv_delivery_name?.text = data[position].TRUENAME
        holder?.itemView?.tv_card_number?.text = "身份证号:${data[position].IDENTITY}"
        holder?.itemView?.tv_delivery_phone?.text = CommonUtils.getEncryPhone(data[position].PHONE.toString())
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}