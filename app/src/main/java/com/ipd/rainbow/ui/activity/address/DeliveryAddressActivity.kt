package com.ipd.rainbow.ui.activity.address

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.fragment.address.DeliveryAddressFragment

/**
Created by Miss on 2018/8/10
收货地址
 */
class DeliveryAddressActivity : BaseUIActivity() {
    companion object {
        val CHOOSE = 0
        val NORMAL = 1
        fun launch(activity: Activity, type: Int = NORMAL) {
            val intent = Intent(activity, DeliveryAddressActivity::class.java)
            intent.putExtra("type",type)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "收货地址"
    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, DeliveryAddressFragment.newInstance(intent.getIntExtra("type", NORMAL))).commit()
    }

    override fun initListener() {
    }

}