package com.ipd.rainbow.ui.activity.store.grouppurchase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.fragment.store.grouppurchase.GroupPurchaseFragment

class GroupPurchaseActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, GroupPurchaseActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "超值团购"
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