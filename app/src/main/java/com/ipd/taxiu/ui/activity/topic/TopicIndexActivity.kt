package com.ipd.taxiu.ui.activity.topic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.Menu
import android.view.MenuItem
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.activity.SearchActivity
import com.ipd.taxiu.ui.fragment.topic.TopicListFragment
import kotlinx.android.synthetic.main.activity_topic_index.*

class TopicIndexActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, TopicIndexActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "话题"

    override fun getContentLayout(): Int = R.layout.activity_topic_index

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    val titles: Array<String> = arrayOf("精选", "最新", "热门", "收藏", "参与")
    override fun loadData() {
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return TopicListFragment.newInstance(position+1)
            }

            override fun getCount(): Int = titles.size
        }
        tab_layout.setViewPager(view_pager, titles)
    }

    override fun initListener() {
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_search) {
            //搜索
            SearchActivity.launch(mActivity, SearchActivity.SearchType.TOPIC)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}