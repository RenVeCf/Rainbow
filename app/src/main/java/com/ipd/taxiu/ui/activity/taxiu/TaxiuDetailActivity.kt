package com.ipd.taxiu.ui.activity.taxiu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taxiu.MainActivity
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.TaxiuBean
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.activity.topic.PublishTopicCommentActivity
import com.ipd.taxiu.ui.fragment.taxiu.TaxiuDetailFragment
import com.ipd.taxiu.widget.MessageDialog
import kotlinx.android.synthetic.main.activity_topic_detail.*
import kotlinx.android.synthetic.main.admin_taxiu_toolbar.*

class TaxiuDetailActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, info: TaxiuBean? = null) {
            val intent = Intent(activity, TaxiuDetailActivity::class.java)
            if (info != null) {
                val bundle = Bundle()
                bundle.putSerializable("info", info)
                intent.putExtras(bundle)
            }
            activity.startActivity(intent)
        }
    }

    private val isAdmin: Boolean by lazy {
        val bundle = intent.extras
        if (bundle != null) {
            val info = bundle.getSerializable("info") as TaxiuBean?
            info?.isMine ?: false
        }else{
            false
        }
    }

    override fun getToolbarLayout(): Int {
        return if (isAdmin) {
            R.layout.admin_taxiu_toolbar
        } else {
            super.getToolbarLayout()
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
        if (isAdmin) {
            fl_delete.setOnClickListener {
                MessageDialog.Builder(mActivity)
                        .setTitle("确认要删除它秀内容吗?")
                        .setMessage("删除后不可恢复，请谨慎操作。")
                        .setCommit("确认删除", { builder ->
                            builder.dismiss()
                        })
                        .setCancel("暂不删除", { builder ->
                            builder.dismiss()
                        }).show()
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isAdmin) {
            return false
        }
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