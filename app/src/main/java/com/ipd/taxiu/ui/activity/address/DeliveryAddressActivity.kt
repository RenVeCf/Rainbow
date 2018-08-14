package com.ipd.taxiu.ui.activity.address

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.address.DeliveryAddressFragment

/**
Created by Miss on 2018/8/10
收货地址
 */
class DeliveryAddressActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, DeliveryAddressActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "收货地址"
    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container,DeliveryAddressFragment.newInstance()).commit()
    }

    override fun initListener() {
    }
}