package com.ipd.taxiu.ui.activity.group

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.GroupDetailAdapter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.widget.MessageDialog
import kotlinx.android.synthetic.main.activity_group_detail.*

/**
Created by Miss on 2018/8/10
拼团详情
 */
class GroupDetailActivity : BaseUIActivity() {
    val list = arrayListOf<String>("", "")
    private val GroupStatus: Int by lazy { intent.getIntExtra("GroupStatus", 1) }

    companion object {
        fun launch(activity: Activity, GroupStatus: Int) {
            val intent = Intent(activity, GroupDetailActivity::class.java)
            intent.putExtra("GroupStatus", GroupStatus)
            activity.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_group_detail
    override fun getToolbarTitle(): String = "拼团详情"

    override fun initView(bundle: Bundle?) {
        initToolbar()
        if (GroupStatus == 3) {
            ll_button.visibility = View.VISIBLE
            ll_button.setOnClickListener { initMessageDialog() }
        }
    }

    private fun initMessageDialog() {
        val builder = MessageDialog.Builder(this)
        builder.setTitle("确认要删除该拼团吗")
        builder.setMessage("拼团信息删除后不可撤销，请谨慎操作。")
        builder.setCommit("确认删除") { builder ->
            toastShow("删除成功")
            builder.dialog.dismiss()
        }
        builder.setCancel("暂不删除") { builder -> builder.dialog.dismiss() }
        builder.dialog.show()
    }

    val adapter: GroupDetailAdapter by lazy {

        GroupDetailAdapter(mActivity, list, GroupStatus)
    }

    override fun loadData() {
        recycler_view_order_detail.layoutManager = LinearLayoutManager(mActivity)
        recycler_view_order_detail.adapter = adapter

        adapter.addFooterView(LayoutInflater.from(this).inflate(R.layout.item_order_detail_footer, null))
        adapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_group_detail_header, null))
    }

    override fun initListener() {
    }
}