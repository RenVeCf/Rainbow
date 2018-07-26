package com.ipd.taxiu.ui.activity.store

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ProductAdapter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.store.ProductListFragment
import kotlinx.android.synthetic.main.product_list_toolbar.*

class ProductListActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, ProductListActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarLayout(): Int = R.layout.product_list_toolbar
    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {

    }

    private val mFragment: ProductListFragment by lazy { ProductListFragment.newInstance() }
    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, mFragment).commit()
    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }
        iv_show_type.setOnClickListener {
            val curShowType = mFragment.switchShowType()
            iv_show_type.setImageResource(if (curShowType == ProductAdapter.ItemType.LIST) R.mipmap.product_list_list else R.mipmap.product_list_grid)
        }

        et_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //搜索

                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

    }
}