package com.ipd.taxiu.ui.activity.message

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity

/**
Created by Miss on 2018/8/13
详情
 */
class MessageDetailActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity){
            val intent = Intent(activity,MessageDetailActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_message_detail
    override fun getToolbarTitle(): String = "详情"

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
    }
}