package com.ipd.taxiu.ui.activity.store

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.store.ClearanceProductFragment
import kotlinx.android.synthetic.main.activity_topic_index.*

class ClearanceProductActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, ClearanceProductActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "商品清仓"

    override fun getContentLayout(): Int = R.layout.activity_topic_index

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    val titles: Array<String> = arrayOf("断码清仓", "临期清仓", "大库存清仓", "反季清仓")
    override fun loadData() {
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return ClearanceProductFragment.newInstance()
            }

            override fun getCount(): Int = titles.size

        }
        tab_layout.setViewPager(view_pager, titles)
    }

    override fun initListener() {
    }

}