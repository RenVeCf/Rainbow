package com.ipd.taxiu.ui.activity.taxiu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taxiu.MainActivity
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.activity.topic.PublishTopicCommentActivity
import com.ipd.taxiu.ui.fragment.taxiu.TaxiuDetailFragment
import kotlinx.android.synthetic.main.activity_topic_detail.*

class TaxiuDetailActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, TaxiuDetailActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "它秀详情"

    override fun getContentLayout(): Int = R.layout.activity_taxiu_detail

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, TaxiuDetailFragment.newInstance()).commit()
    }

    override fun initListener() {
        tv_comment_topic.setOnClickListener {
            //参与话题
            PublishTopicCommentActivity.launch(mActivity)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.topic_detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_index) {
            //首页
            MainActivity.launch(mActivity)
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}