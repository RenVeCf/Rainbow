package com.ipd.taxiu.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIFragment
import com.ipd.taxiu.ui.activity.SearchActivity
import com.ipd.taxiu.ui.fragment.taxiu.TaxiuListFragment
import kotlinx.android.synthetic.main.fragment_taxiu_index.view.*

class TaxiuFragment : BaseUIFragment() {

    override fun getTitleLayout(): Int = -1

    override fun getContentLayout(): Int = R.layout.fragment_taxiu_index

    override fun initView(bundle: Bundle?) {

    }

    val titles: Array<String> = arrayOf("精选", "推荐", "最新", "关注", "猫咪", "狗狗")
    override fun loadData() {
        mContentView.view_pager.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return TaxiuListFragment.newInstance(position)
            }

            override fun getCount(): Int = titles.size
        }
        mContentView.tab_layout.setViewPager(mContentView.view_pager, titles)
    }


    override fun initListener() {
        mContentView.tv_search.setOnClickListener {
            SearchActivity.launch(mActivity, SearchActivity.SearchType.TAXIU)
        }
    }

}