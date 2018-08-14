package com.ipd.taxiu.ui.activity.coupon

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.coupon.DiscountCouponFragment

/**
Created by Miss on 2018/8/10
我的优惠券
 */
class DiscountCouponActivity : BaseUIActivity() {
    companion object {
        fun launch(activity:Activity){
            val intent = Intent(activity,DiscountCouponActivity::class.java)
            activity.startActivity(intent)
        }
    }
    override fun getContentLayout(): Int = R.layout.activity_container
    override fun getToolbarTitle(): String = "我的优惠券"

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container,DiscountCouponFragment.newInstance()).commit()
    }

    override fun initListener() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_integral_exhcange,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id:Int = item!!.itemId
        if (id == R.id.integral_exchange){
            startActivity(Intent(this,IntegralExchangeActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}