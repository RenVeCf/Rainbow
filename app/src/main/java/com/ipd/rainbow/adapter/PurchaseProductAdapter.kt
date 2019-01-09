package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.PurchaseProductBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.utils.StringUtils
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

        ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_group_purchase_img)
        holder.itemView.tv_group_purchase_name.text = info.PROCUCT_NAME
        holder.itemView.tv_group_purchase_price.text = "￥" + info.CURRENT_PRICE
        holder.itemView.tv_group_purchase_info.text = "${info.TEAM_NUM}人团，${info.JOIN_NUM}人已参团"


        val surplusTime = info.endTime - System.currentTimeMillis()
        StringUtils.getCountDownByTime(surplusTime, { hours, minutes, second ->
            holder.itemView.tv_group_purchase_hours.text = hours
            holder.itemView.tv_group_purchase_minute.text = minutes
            holder.itemView.tv_group_purchase_second.text = second
        })

        if (info.JOIN_STATUS == 0){
            holder.itemView.tv_group_purchase_buy.setBackgroundResource(R.drawable.shape_buy_bg)
            holder.itemView.tv_group_purchase_buy.setTextColor(context.resources.getColor(R.color.white))
            holder.itemView.tv_group_purchase_buy.text = "立即参团"
        }else{
            holder.itemView.tv_group_purchase_buy.setBackgroundResource(R.drawable.shape_order_btn_cancel)
            holder.itemView.tv_group_purchase_buy.setTextColor(context.resources.getColor(R.color.LightGrey))
            holder.itemView.tv_group_purchase_buy.text = "已参团"
        }

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}