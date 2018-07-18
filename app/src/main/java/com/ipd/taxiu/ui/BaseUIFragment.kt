package com.ipd.taxiu.ui

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.widget.ProgressLayout

/**
 * Created by jumpbox on 2017/7/5.
 */
abstract class BaseUIFragment : BaseFragment() {
    protected lateinit var mHeaderView: ViewGroup
    protected lateinit var mContentView: ViewGroup
    protected lateinit var progress_layout: ProgressLayout


    override fun getBaseLayout(): Int {
        return R.layout.fragment_base
    }

    override fun initBaseLayout(rootView: ViewGroup?) {
        //初始化标题栏
        val titleLayoutRes = getTitleLayout()
        if (titleLayoutRes > 0) {
            mHeaderView = rootView?.findViewById(R.id.layout_header)!!
            mHeaderView.addView(LayoutInflater.from(mActivity).inflate(titleLayoutRes, mHeaderView, false))
            initTitle()
        }
        //初始化内容区域
        mContentView = rootView?.findViewById(R.id.content_container)!!
        mContentView.addView(LayoutInflater.from(mActivity).inflate(getContentLayout(), mContentView, false))
        progress_layout = getProgressLayout()

    }


    protected open fun getTitleLayout(): Int {
        return R.layout.base_toolbar
    }


    protected open fun initTitle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mHeaderView.outlineProvider = null
        }
    }

    protected open fun getProgressLayout(): ProgressLayout {
        return mRootView?.findViewById(R.id.progress_layout)!!
    }


    protected abstract fun getContentLayout(): Int

    protected fun showProgress() {
        progress_layout.showLoading()
    }

    protected fun showError() {
        showError(getString(R.string.loading_error))
    }

    protected fun showError(errMsg: String) {
        showError(errMsg, View.OnClickListener { loadData() })
    }

    protected fun showError(errMsg: String, clickListener: View.OnClickListener) {
        progress_layout.showError(errMsg, clickListener)
    }

    protected fun showContent() {
        progress_layout.showContent()
    }
}