package com.ipd.rainbow.ui.activity.store

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.StoreSearchHistroyBean
import com.ipd.rainbow.presenter.store.StoreSearchPresenter
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.widget.MessageDialog
import kotlinx.android.synthetic.main.activity_store_search.*
import kotlinx.android.synthetic.main.search_store_toolbar.*

class StoreSearchActivity : BaseUIActivity(), StoreSearchPresenter.IStoreSearchView {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, StoreSearchActivity::class.java)
            activity.startActivity(intent)
        }
    }


    override fun getToolbarLayout(): Int = R.layout.search_store_toolbar

    override fun getContentLayout(): Int = R.layout.activity_store_search


    private var mPresenter: StoreSearchPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = StoreSearchPresenter()
        mPresenter?.attachView(mActivity, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }

    override fun initView(bundle: Bundle?) {

    }

    override fun loadData() {
        mPresenter?.loadSearchHistory()
    }

    override fun loadSearchHistorySuccess(info: StoreSearchHistroyBean) {
        info.data?.forEach { info ->
            val lableView = LayoutInflater.from(mActivity).inflate(R.layout.item_lable, hot_search_layout, false) as TextView
            lableView.text = info.NAME
            lableView.setOnClickListener {
                searchProduct(info.NAME)
            }
            hot_search_layout.addView(lableView)
        }
        info.historyList?.forEach { info ->
            val lableView = LayoutInflater.from(mActivity).inflate(R.layout.item_lable, hot_search_layout, false) as TextView
            lableView.text = info.NAME
            lableView.setOnClickListener {
                searchProduct(info.NAME)
            }
            history_search_layout.addView(lableView)
        }
    }

    override fun loadSearchHistoryFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun clearSearchHisSuccess() {
        history_search_layout.removeAllViews()
    }

    override fun clearSearchHisFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }
        tv_cancel.setOnClickListener { finish() }

        et_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //搜索
                val searchKey = et_search.text.toString().trim()
                if (TextUtils.isEmpty(searchKey)) {
                    toastShow("请输入搜索关键字")
                    return@setOnEditorActionListener false
                }
                searchProduct(searchKey)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        tv_clear_history.setOnClickListener {
            val builder = MessageDialog.Builder(mActivity)
            builder.setTitle("提示")
                    .setMessage("确定清空历史搜索记录?")
                    .setCommit("确定", {
                        it.dismiss()
                        mPresenter?.clearSearchHistory()
                    })
                    .setCancel("取消", {
                        it.dismiss()
                    }).show()
        }
    }

    private fun searchProduct(key: String) {
        ProductListActivity.launch(mActivity, searchKey = key)
        finish()
    }

}