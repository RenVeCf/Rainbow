package com.ipd.taxiu.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.SearchFragment
import kotlinx.android.synthetic.main.search_toolbar.*

class SearchActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity, searchType: SearchType) {
            val intent = Intent(activity, SearchActivity::class.java)
            intent.putExtra("searchType", searchType)
            activity.startActivity(intent)
        }
    }

    enum class SearchType(type: Int) {
        TOPIC(0), TALK(1), CLASSROOM(2), TAXIU(3)
    }

    private val mSearchType: SearchType by lazy { intent.getSerializableExtra("searchType") as SearchType }
    override fun getToolbarLayout(): Int = R.layout.search_toolbar

    override fun getContentLayout(): Int = R.layout.activity_container

    private val searchFragment by lazy { SearchFragment.newInstance(mSearchType) }
    override fun initView(bundle: Bundle?) {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, searchFragment).commit()
    }

    override fun loadData() {

    }

    override fun initListener() {
        tv_cancel.setOnClickListener { finish() }

        val inputMethodManager = (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
        et_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //搜索
                val searchStr = et_search.text.toString().trim()
                if (TextUtils.isEmpty(searchStr)) {
                    toastShow("请输入搜索内容")
                    return@setOnEditorActionListener false
                }
                inputMethodManager.hideSoftInputFromWindow(et_search.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                searchFragment.search(searchStr)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}