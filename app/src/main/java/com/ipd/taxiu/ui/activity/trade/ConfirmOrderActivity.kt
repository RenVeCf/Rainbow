package com.ipd.taxiu.ui.activity.trade

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ConfirmOrderProductAdapter
import com.ipd.taxiu.bean.CartProductBean
import com.ipd.taxiu.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_confirm_order.*
import kotlinx.android.synthetic.main.layout_order_product.*

class ConfirmOrderActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, ConfirmOrderActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "确认订单"
    override fun getContentLayout(): Int = R.layout.activity_confirm_order

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        val list = arrayListOf(CartProductBean(), CartProductBean(), CartProductBean())
        product_recycler_view.adapter = ConfirmOrderProductAdapter(mActivity, list, {

        })
    }

    override fun initListener() {
        tv_confirm.setOnClickListener {
            toastShow(true, "支付成功")
            finish()
        }
    }

}
