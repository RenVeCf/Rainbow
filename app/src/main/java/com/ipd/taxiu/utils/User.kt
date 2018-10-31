package com.ipd.taxiu.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ipd.taxiu.R

object User {
    fun setAttentBtnStyle(context: Context, isAttent: Int, ll_attent: LinearLayout) {
        val iv_attent = ll_attent.findViewById<ImageView>(R.id.iv_attent)
        val tv_attent = ll_attent.findViewById<TextView>(R.id.tv_attent)
        when (isAttent) {
            0 -> {
                ll_attent.setBackgroundResource(R.drawable.shape_buy_bg)
                iv_attent.visibility = View.VISIBLE
                tv_attent.text = "关注"
                tv_attent.setTextColor(context.resources.getColor(R.color.white))
            }
            1 -> {
                ll_attent.setBackgroundResource(R.drawable.shape_order_btn_cancel)
                iv_attent.visibility = View.GONE
                tv_attent.text = "已关注"
                tv_attent.setTextColor(context.resources.getColor(R.color.LightGrey))
            }
            2 -> {
                ll_attent.setBackgroundResource(R.drawable.shape_order_btn_cancel)
                iv_attent.visibility = View.GONE
                tv_attent.text = "互相关注"
                tv_attent.setTextColor(context.resources.getColor(R.color.LightGrey))
            }
        }
    }
}