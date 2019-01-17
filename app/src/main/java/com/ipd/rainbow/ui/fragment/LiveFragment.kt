package com.ipd.rainbow.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIFragment
import com.ipd.rainbow.ui.fragment.live.LiveListFragment
import kotlinx.android.synthetic.main.fragment_live_index.view.*

class LiveFragment : BaseUIFragment() {

    override fun getTitleLayout(): Int = -1

    override fun getContentLayout(): Int = R.layout.fragment_live_index

    override fun initView(bundle: Bundle?) {

    }

    val titles: Array<String> = arrayOf("精选", "推荐", "最新", "关注", "猫咪", "狗狗")
    override fun loadData() {
        mContentView.view_pager.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return LiveListFragment.newInstance(position + 1)
            }

            override fun getCount(): Int = titles.size
        }
        mContentView.tab_layout.setViewPager(mContentView.view_pager, titles)
    }


    override fun initListener() {

    }

}