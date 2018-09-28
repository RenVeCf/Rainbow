package com.ipd.taxiu.adapter

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BankCardBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.ui.activity.balance.AddBankCardActivity
import com.ipd.taxiu.ui.activity.balance.BankCardActivity
import com.ipd.taxiu.ui.activity.balance.MyBalanceActivity
import kotlinx.android.synthetic.main.item_bank_card.view.*

/**
Created by Miss on 2018/8/13
 */
class BankCardAdapter(val context: Context, private val data: List<BankCardBean>?, private val bankType: Int) : RecyclerView.Adapter<BankCardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bank_card, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = data!![position]
        ImageLoader.loadNoPlaceHolderImg(context, info.BANK_LOGO, holder.itemView.iv_bank_icon)
        holder.itemView.tv_bank_title.text = info.BANK_NAME
        holder.itemView.tv_bank_no.text = "尾号（${info.TAIL_NUM}）"

        holder?.itemView?.setOnClickListener {
            if (bankType == MyBalanceActivity.ADD_BANK_CARD) {
                AddBankCardActivity.launch(context as Activity, MyBalanceActivity.UPDATE_BANK_CARD,info.BANK_CARD_ID.toString())
            } else if (bankType == MyBalanceActivity.CHOSSE_BANK_CARD) {
                val activity = context as BankCardActivity
                val intent = Intent()
                intent.putExtra("bankCard", info.BANK_NAME)
                activity.setResult(RESULT_OK, intent)
                activity.finish()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}