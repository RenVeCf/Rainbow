package com.ipd.taxiu.ui.activity.store

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_store_search.*
import kotlinx.android.synthetic.main.search_store_toolbar.*

class StoreSearchActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, StoreSearchActivity::class.java)
            activity.startActivity(intent)
        }
    }


    override fun getToolbarLayout(): Int = R.layout.search_store_toolbar

    override fun getContentLayout(): Int = R.layout.activity_store_search

    override fun initView(bundle: Bundle?) {

    }

    override fun loadData() {
        for (index: Int in 0 until 10) {
            val lableView = LayoutInflater.from(mActivity).inflate(R.layout.item_lable, hot_search_layout, false)
            hot_search_layout.addView(lableView)
            val lableView2 = LayoutInflater.from(mActivity).inflate(R.layout.item_lable, history_search_layout, false)
            history_search_layout.addView(lableView2)
        }

    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }
        tv_cancel.setOnClickListener { finish() }

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