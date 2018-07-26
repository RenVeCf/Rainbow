package com.ipd.taxiu.ui.activity.store.grouppurchase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.store.grouppurchase.GroupPurchaseFragment

class GroupPurchaseActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, GroupPurchaseActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "优惠团购"
    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, GroupPurchaseFragment.newInstance()).commit()
    }

    override fun initListener() {


    }
}