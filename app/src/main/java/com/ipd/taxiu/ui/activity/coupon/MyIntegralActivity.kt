package com.ipd.taxiu.ui.activity.coupon

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.coupon.MyIntegralFragment

/**
Created by Miss on 2018/8/10
我的积分
 */
class MyIntegralActivity : BaseUIActivity() {
    override fun getContentLayout(): Int = R.layout.activity_container
    override fun getToolbarTitle(): String = "我的积分"

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container,MyIntegralFragment.newInstance()).commit()
    }

    override fun initListener() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_integral_rule,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id:Int = item!!.itemId
        if (id == R.id.integral_rule) IntegralRuleActivity.launch(this)
        return super.onOptionsItemSelected(item)
    }
}