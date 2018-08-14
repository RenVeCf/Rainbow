package com.ipd.taxiu.ui.activity.coupon

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity

/**
Created by Miss on 2018/8/10
兑换记录详情
 */
class ExchangeRecordDetailActivity : BaseUIActivity() {
    companion object {
        fun launch(activity:Activity){
            val intent = Intent(activity,ExchangeRecordDetailActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_exchange_record_detail
    override fun getToolbarTitle(): String = "兑换记录详情"

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
    }
}