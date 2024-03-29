package com.ipd.rainbow.ui.activity.coupon

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.rainbow.R
import com.ipd.rainbow.platform.http.HttpUrl
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.activity.web.WebActivity
import com.ipd.rainbow.ui.fragment.coupon.MyIntegralFragment

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
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, MyIntegralFragment.newInstance()).commit()
    }

    override fun initListener() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_integral_rule, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id: Int = item!!.itemId
        if (id == R.id.integral_rule) {
//            IntegralRuleActivity.launch(this)
            WebActivity.launch(mActivity,WebActivity.URL,HttpUrl.WEB_URL+HttpUrl.INTEGRAL_RULE,"积分规则")
        }
        return super.onOptionsItemSelected(item)
    }
}