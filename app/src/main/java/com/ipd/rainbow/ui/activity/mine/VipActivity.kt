package com.ipd.rainbow.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIActivity


class VipActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, VipActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "彩虹会员"

    override fun getContentLayout(): Int = R.layout.activity_vip


    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {

    }


}