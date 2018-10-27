package com.ipd.taxiu.utils

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.ipd.taxiu.R
import kotlinx.android.synthetic.main.layout_topic_people_comment_header.view.*

object User {
    fun setAttentBtnStyle(context: Context, isAttent: Int, ll_attent: LinearLayout) {
        when (isAttent) {
            0 -> {
                ll_attent.setBackgroundResource(R.drawable.shape_buy_bg)
                ll_attent.iv_attent.visibility = View.VISIBLE
                ll_attent.tv_attent.text = "关注"
                ll_attent.tv_attent.setTextColor(context.resources.getColor(R.color.white))
            }
            1 -> {
                ll_attent.setBackgroundResource(R.drawable.shape_order_btn_cancel)
                ll_attent.iv_attent.visibility = View.GONE
                ll_attent.tv_attent.text = "已关注"
                ll_attent.tv_attent.setTextColor(context.resources.getColor(R.color.LightGrey))
            }
            2 -> {
                ll_attent.setBackgroundResource(R.drawable.shape_order_btn_cancel)
                ll_attent.iv_attent.visibility = View.GONE
                ll_attent.tv_attent.text = "互相关注"
                ll_attent.tv_attent.setTextColor(context.resources.getColor(R.color.white))
            }
        }
    }
}