package com.ipd.taxiu.ui.activity.mine.published

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.topic.MineTopicListFragment
import com.ipd.taxiu.ui.fragment.topic.TopicListFragment

class MineJoinTopicActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, MineJoinTopicActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "我参与的话题"

    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, MineTopicListFragment.newInstance()).commit()

    }

    override fun initListener() {
    }

}