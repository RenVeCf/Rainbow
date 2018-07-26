package com.ipd.taxiu.ui.activity.store.flashsale

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.store.flashsale.FlashSaleListFragment

class FlashSaleActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, FlashSaleActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "限时抢购"
    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, FlashSaleListFragment.newInstance()).commit()
    }

    override fun initListener() {


    }
}