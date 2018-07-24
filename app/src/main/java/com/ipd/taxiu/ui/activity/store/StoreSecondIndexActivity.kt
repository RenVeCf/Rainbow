package com.ipd.taxiu.ui.activity.store

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.store.StoreSecondIndexFragment
import com.ipd.taxiu.utils.StorePetSpecialType
import com.ipd.taxiu.widget.CurFragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_store_second_index.*
import kotlinx.android.synthetic.main.second_store_toolbar.*

class StoreSecondIndexActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, type: Int) {
            val intent = Intent(activity, StoreSecondIndexActivity::class.java)
            intent.putExtra("type", type)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarLayout(): Int = R.layout.second_store_toolbar
    override fun getContentLayout(): Int = R.layout.activity_store_second_index

    private val mType: Int by lazy { intent.getIntExtra("type", StorePetSpecialType.SMALL_DOG) }
    override fun initView(bundle: Bundle?) {

    }

    override fun loadData() {
        view_pager.adapter = object : CurFragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return StoreSecondIndexFragment.newInstance(mType)
            }

            override fun getCount(): Int {
                return when (StorePetSpecialType.getParentTypeByType(mType)) {
                    StorePetSpecialType.DOG -> StorePetSpecialType.DOG_TABS.size
                    StorePetSpecialType.CAT -> StorePetSpecialType.CAT_TABS.size
                    else -> 0
                }
            }

            override fun getPageTitle(position: Int): CharSequence {
                return when (StorePetSpecialType.getParentTypeByType(mType)) {
                    StorePetSpecialType.DOG -> StorePetSpecialType.DOG_TAB_TITLES[position]
                    StorePetSpecialType.CAT -> StorePetSpecialType.CAT_TAB_TITLES[position]
                    else -> ""
                }
            }

        }
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }
        iv_store_home.setOnClickListener { finish() }
        iv_scroll_top.setOnClickListener {
            if (view_pager.adapter != null && view_pager.adapter is CurFragmentPagerAdapter){
                val curFragmentPagerAdapter = view_pager.adapter as CurFragmentPagerAdapter
                (curFragmentPagerAdapter.mCurrentFragment as StoreSecondIndexFragment).scrollTop()
            }
        }

    }
}