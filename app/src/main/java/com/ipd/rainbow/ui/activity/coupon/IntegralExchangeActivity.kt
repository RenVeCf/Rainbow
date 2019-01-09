package com.ipd.rainbow.ui.activity.coupon

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.fragment.coupon.IntegralExchangeFragment

/**
Created by Miss on 2018/8/10
积分兑换
 */
class IntegralExchangeActivity:BaseUIActivity() {
    companion object {
        fun launch(activity:Activity){
            val intent = Intent(activity,IntegralExchangeActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_container
    override fun getToolbarTitle(): String ="积分兑换"

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container,IntegralExchangeFragment.newInstance()).commit()
    }

    override fun initListener() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exchange_record,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id:Int = item!!.itemId
        if (id == R.id.integral_record){
            ExchangeRecordActivity.launch(this)
        }
        return super.onOptionsItemSelected(item)
    }
}