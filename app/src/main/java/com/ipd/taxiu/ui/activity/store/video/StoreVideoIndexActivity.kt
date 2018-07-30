package com.ipd.taxiu.ui.activity.store.video

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.store.StoreVideoListFragment
import kotlinx.android.synthetic.main.activity_store_video_index.*

class StoreVideoIndexActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, StoreVideoIndexActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "潮品视频"

    override fun getContentLayout(): Int = R.layout.activity_store_video_index

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    val titles: Array<String> = arrayOf("全部", "热门", "狗粮", "零食", "保健", "清洁", "日用")
    override fun loadData() {
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return StoreVideoListFragment.newInstance(position)
            }

            override fun getCount(): Int = titles.size

            override fun getPageTitle(position: Int): CharSequence {
                return titles[position]
            }
        }
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun initListener() {
    }

}