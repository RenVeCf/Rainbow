package com.ipd.taxiu.ui.activity.topic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.topic.TopicPeopleCommentFragment

class TopicPeopleCommentActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, TopicPeopleCommentActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "某某人的评论"

    override fun getContentLayout(): Int = R.layout.activity_topic_people_recommend

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, TopicPeopleCommentFragment.newInstance()).commit()
    }

    override fun initListener() {

    }

}