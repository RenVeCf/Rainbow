package com.ipd.taxiu.ui.activity.store

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.inputmethod.EditorInfo
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.store.ProductCategoryFragment
import kotlinx.android.synthetic.main.activity_product_category_index.*
import kotlinx.android.synthetic.main.product_category_toolbar.*

class ProductCategoryActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, ProductCategoryActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarLayout(): Int = R.layout.product_category_toolbar

    override fun getContentLayout(): Int = R.layout.activity_product_category_index

    override fun initView(bundle: Bundle?) {

    }

    val titles: Array<String> = arrayOf("狗狗", "猫咪", "品牌")
    override fun loadData() {
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> ProductCategoryFragment.newInstance(0)
                    1 -> ProductCategoryFragment.newInstance(1)
                    else -> ProductBrandFragment.newInstance()
                }
            }

            override fun getCount(): Int = titles.size
        }
        tab_layout.setViewPager(view_pager, titles)
    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }
        et_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //搜索
                ProductListActivity.launch(mActivity)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

    }

}