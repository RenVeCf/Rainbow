package com.ipd.taxiu.ui.activity.store

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.store.StoreSpecialFragment
import kotlinx.android.synthetic.main.second_store_toolbar.*

class StoreSpecialActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, StoreSpecialActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarLayout(): Int = R.layout.second_store_toolbar
    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {

    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, StoreSpecialFragment.newInstance()).commit()
    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }
    }
}