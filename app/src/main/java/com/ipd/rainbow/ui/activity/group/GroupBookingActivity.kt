package com.ipd.rainbow.ui.activity.group

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.fragment.group.GroupFragment
import kotlinx.android.synthetic.main.activity_talk_index.*

/**
Created by Miss on 2018/8/10
我的拼团
 */
class GroupBookingActivity : BaseUIActivity() {
    private val titles = arrayOf("待成团", "已成团", "未成团")

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, GroupBookingActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_group_index
    override fun getToolbarTitle(): String = "我的拼团"

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment = GroupFragment.newInstance(position + 1)
            override fun getCount(): Int = titles.size
        }
        tab_layout.setViewPager(view_pager, titles)
    }

    override fun initListener() {
    }

}