package com.ipd.taxiu.ui.activity.talk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taxiu.MainActivity
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.TalkBean
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.talk.TalkDetailFragment
import kotlinx.android.synthetic.main.activity_topic_detail.*

class TalkDetailActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, info: TalkBean? = null) {
            val intent = Intent(activity, TalkDetailActivity::class.java)
            if (info != null) {
                val bundle = Bundle()
                bundle.putSerializable("info", info)
                intent.putExtras(bundle)
            }
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = if (isMine) "我的提问" else "小不点贝贝的提问"

    override fun getContentLayout(): Int = R.layout.activity_talk_detail

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    private val isMine: Boolean by lazy {
        val bundle = intent.extras
        if (bundle != null) {
            val info = bundle.getSerializable("info") as TalkBean?
            info?.isMine ?: false
        } else {
            false
        }
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, TalkDetailFragment.newInstance(isMine)).commit()
    }

    override fun initListener() {
        tv_comment_topic.setOnClickListener {
            //参与话题

        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isMine) {
            menuInflater.inflate(R.menu.menu_share, menu)
        } else {
            menuInflater.inflate(R.menu.topic_detail_menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_index) {
            //首页
            MainActivity.launch(mActivity)
            return true
        } else if (id == R.id.action_share) {
            //分享

            return true
        }

        return super.onOptionsItemSelected(item)
    }
}