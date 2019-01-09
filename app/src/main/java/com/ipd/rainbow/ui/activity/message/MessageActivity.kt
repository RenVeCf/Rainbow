package com.ipd.rainbow.ui.activity.message

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.fragment.message.MessageFragment
import kotlinx.android.synthetic.main.activity_message_index.*

/**
Created by Miss on 2018/8/13
消息
 */
class MessageActivity : BaseUIActivity() {
    val titles = arrayOf("订单消息", "系统消息", "其他消息")

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, MessageActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_message_index
    override fun getToolbarTitle(): String = "消息"

    private val mPos by lazy { intent?.getIntExtra("pos", 0) ?: 0 }
    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment = MessageFragment.newInstance(position + 1)

            override fun getCount(): Int = titles.size

        }
        tab_layout.setViewPager(view_pager, titles)
        view_pager.currentItem = mPos
    }

    override fun initListener() {
    }
}