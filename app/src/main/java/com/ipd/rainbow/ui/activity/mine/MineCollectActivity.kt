package com.ipd.rainbow.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.fragment.mine.CollectStoreFragment

class MineCollectActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, MineCollectActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "我的收藏"
    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()

    }

    private val mFragment: CollectStoreFragment by lazy { CollectStoreFragment.newInstance() }
    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, mFragment).commit()
    }

    override fun initListener() {
    }
}