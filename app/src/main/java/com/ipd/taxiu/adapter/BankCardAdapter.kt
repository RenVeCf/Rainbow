package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BankCardBean
import com.ipd.taxiu.ui.activity.balance.BankCardActivity
import com.ipd.taxiu.ui.activity.balance.UpdateBankCardActivity
import kotlinx.android.synthetic.main.item_bank_card.view.*

/**
Created by Miss on 2018/8/13
 */
class BankCardAdapter(val context: Context,private val data:List<BankCardBean>,private val bankType:String) : RecyclerView.Adapter<BankCardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bank_card,parent,false))
    }

    override fun getItemCount(): Int = data?.size ?:0

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.itemView?.iv_bank_icon?.setImageResource(data[position].iconRes)
        holder?.itemView?.tv_bank_title?.text = data[position].title

        holder?.itemView?.setOnClickListener {
            if (bankType == "添加银行卡") {
                UpdateBankCardActivity.launch(context as Activity)
            } else if (bankType == "选择银行卡") {
                val activity = context as BankCardActivity
                activity.finish()
            }
        }
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
}