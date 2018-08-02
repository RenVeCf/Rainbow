package com.ipd.taxiu.ui.activity.mine.published

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.mine.published.PublishedTaxiuFragment
import kotlinx.android.synthetic.main.activity_published_taxiu.*

class PublishedTaxiuActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, PublishedTaxiuActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "它秀"

    override fun getContentLayout(): Int = R.layout.activity_published_taxiu

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    val titles: Array<String> = arrayOf("我发布的", "我评论的")
    override fun loadData() {
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return PublishedTaxiuFragment.newInstance(position)
            }

            override fun getCount(): Int = titles.size
        }
        tab_layout.setViewPager(view_pager, titles)
    }

    override fun initListener() {

    }
}