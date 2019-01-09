package com.ipd.rainbow.ui.activity.balance

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.fragment.balance.BalanceBillFragment

/**
Created by Miss on 2018/8/10
余额账单
 */
class BalanceBillActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, BalanceBillActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_container
    override fun getToolbarTitle(): String = "余额账单"
    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, BalanceBillFragment.newInstance()).commit()
    }

    override fun initListener() {
    }
}