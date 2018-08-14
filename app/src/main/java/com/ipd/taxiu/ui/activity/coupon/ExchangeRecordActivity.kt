package com.ipd.taxiu.ui.activity.coupon

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.coupon.ExchangeRecordFragment

/**
Created by Miss on 2018/8/10
兑换记录
 */
class ExchangeRecordActivity:BaseUIActivity() {
    companion object {
        fun launch(activity:Activity){
            val intent = Intent(activity,ExchangeRecordActivity::class.java)
            activity.startActivity(intent)
        }
    }
    override fun getContentLayout(): Int = R.layout.activity_container
    override fun getToolbarTitle(): String ="兑换记录"

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container,ExchangeRecordFragment.newInstance()).commit()
    }

    override fun initListener() {
    }
}