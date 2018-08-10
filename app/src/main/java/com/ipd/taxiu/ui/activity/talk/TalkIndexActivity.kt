package com.ipd.taxiu.ui.activity.talk

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
import com.ipd.taxiu.ui.fragment.talk.TalkListFragment
import kotlinx.android.synthetic.main.activity_talk_index.*

class TalkIndexActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, TalkIndexActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "问答"

    override fun getContentLayout(): Int = R.layout.activity_talk_index

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    val titles: Array<String> = arrayOf("精选", "最新", "热门", "提问", "回答")
    override fun loadData() {
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return TalkListFragment.newInstance(position)
            }

            override fun getCount(): Int = titles.size
        }
        tab_layout.setViewPager(view_pager, titles)
    }

    override fun initListener() {
        iv_publish_talk.setOnClickListener {
            //发布问答
            PublishTalkActivity.launch(mActivity)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_search) {
            //搜索
            SearchActivity.launch(mActivity, SearchActivity.SearchType.TALK)
            return true
        }

        return super.onOptionsItemSelected(item)
    }


}