package com.ipd.rainbow.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIFragment
import com.ipd.rainbow.ui.fragment.message.MessageFragment
import kotlinx.android.synthetic.main.fragment_live_index.view.*

class HomeMessageFragment : BaseUIFragment() {

    override fun getTitleLayout(): Int = R.layout.msg_toolbar

    override fun getContentLayout(): Int = R.layout.activity_message_index

    override fun initView(bundle: Bundle?) {
    }

    val titles: Array<String> = arrayOf("订单消息", "系统消息", "活动消息")
    override fun loadData() {
        mContentView.view_pager.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return MessageFragment.newInstance(position + 1)
            }

            override fun getCount(): Int = titles.size
        }
        mContentView.tab_layout.setViewPager(mContentView.view_pager, titles)
    }


    override fun initListener() {

    }

}