package com.ipd.taxiu.ui.activity.store

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ProductAdapter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.store.ProductListFragment
import kotlinx.android.synthetic.main.product_list_toolbar.*

class ProductListActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, searchKey: String = "") {
            val intent = Intent(activity, ProductListActivity::class.java)
            intent.putExtra("searchKey", searchKey)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarLayout(): Int = R.layout.product_list_toolbar
    override fun getContentLayout(): Int = R.layout.activity_container

    private var mSearchKey: String = ""
    override fun initView(bundle: Bundle?) {
        mSearchKey = intent.getStringExtra("searchKey")
        et_search.setText(mSearchKey)

    }

    private lateinit var mFragment: ProductListFragment
    override fun loadData() {
        mFragment = ProductListFragment.newInstance(mSearchKey)
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, mFragment).commit()
    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }
        iv_show_type.setOnClickListener {
            val curShowType = mFragment.switchShowType()
            iv_show_type.setImageResource(if (curShowType == ProductAdapter.ItemType.LIST) R.mipmap.product_list_list else R.mipmap.product_list_grid)
        }

        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        et_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //搜索
                val searchKey = et_search.text.toString().trim()
                mSearchKey = searchKey
                mFragment.onSearch(searchKey)

                manager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

    }
}