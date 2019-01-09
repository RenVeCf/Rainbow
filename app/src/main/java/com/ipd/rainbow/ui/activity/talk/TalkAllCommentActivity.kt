package com.ipd.rainbow.ui.activity.talk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.fragment.talk.TalkAllCommentFragment

class TalkAllCommentActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, talkId: Int = -1, isMine: Boolean, isSure: Int) {
            val intent = Intent(activity, TalkAllCommentActivity::class.java)
            intent.putExtra("talkId", talkId)
            intent.putExtra("isMine", isMine)
            intent.putExtra("isSure", isSure)
            activity.startActivity(intent)
        }
    }

    private val talkId: Int by lazy { intent.getIntExtra("talkId", -1) }
    private val isSure: Int by lazy { intent.getIntExtra("isSure", -1) }
    private val isMine: Boolean by lazy { intent.getBooleanExtra("isMine", false) }

    override fun getToolbarTitle(): String = "用户回答"

    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, TalkAllCommentFragment.newInstance(talkId,isMine,isSure)).commit()
    }

    override fun initListener() {
    }


}