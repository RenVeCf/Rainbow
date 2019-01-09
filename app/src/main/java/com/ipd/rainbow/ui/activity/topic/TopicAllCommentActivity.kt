package com.ipd.rainbow.ui.activity.topic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.fragment.topic.TopicAllCommentFragment

class TopicAllCommentActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, topicId: Int = -1) {
            val intent = Intent(activity, TopicAllCommentActivity::class.java)
            intent.putExtra("topicId", topicId)
            activity.startActivity(intent)
        }
    }

    private val topicId: Int by lazy { intent.getIntExtra("topicId", -1) }

    override fun getToolbarTitle(): String = "用户评论"

    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, TopicAllCommentFragment.newInstance(topicId)).commit()
    }

    override fun initListener() {
    }


}