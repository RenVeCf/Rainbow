package com.ipd.taxiu.ui.activity.store

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.store.NewProductListFragment

class NewProductListActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, NewProductListActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "新品上新"
    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()

    }

    private val mFragment: NewProductListFragment by lazy { NewProductListFragment.newInstance() }
    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, mFragment).commit()
    }

    override fun initListener() {
    }
}