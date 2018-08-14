package com.ipd.taxiu.ui.activity.coupon

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.widget.MessageDialog
import kotlinx.android.synthetic.main.activity_my_integral.*

/**
Created by Miss on 2018/8/10
 */
class CouponDetailActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, CouponDetailActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_coupon_detail
    override fun getToolbarTitle(): String = "优惠券详情"

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
        btn_exchange.setOnClickListener { initMessageDialog() }
    }

    private fun initMessageDialog() {
        val builder = MessageDialog.Builder(this)
        builder.setTitle("确认要兑换该优惠券吗？")
        builder.setMessage("兑换后发放至我的-优惠券列表中")
        builder.setCommit("确认兑换", { builder ->
            toastShow("兑换成功")
            builder.dialog.dismiss()
        })
        builder.setCancel("暂不兑换",{builder ->
            builder.dialog.dismiss()
        })
        builder.dialog.show()

    }
}