package com.ipd.taxiu.ui

import android.os.Build
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.ipd.taxiu.R
import com.ipd.taxiu.widget.ProgressLayout

/**
 * Created by jumpbox on 2017/7/20.
 */
abstract class BaseUIActivity : BaseActivity() {

    val progress_layout: ProgressLayout by lazy { findViewById<ProgressLayout>(R.id.progress_layout) }
    lateinit var mContentView: ViewGroup
    lateinit var mHeaderView: ViewGroup

    override fun getBaseLayout(): Int {
        return R.layout.activity_base
    }

    override fun initBaseLayout() {
        mHeaderView = findViewById(R.id.layout_header)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mHeaderView.outlineProvider = null
        }

        val toolbarLayoutRes = getToolbarLayout()
        if (toolbarLayoutRes >= 0) {
            mHeaderView.addView(LayoutInflater.from(mActivity).inflate(toolbarLayoutRes, mHeaderView, false))
        }

        mContentView = findViewById(R.id.content_container)
        mContentView.addView(LayoutInflater.from(mActivity).inflate(getContentLayout(), mContentView, false))
    }


    open protected fun getToolbarLayout(): Int {
        return R.layout.base_toolbar
    }


    open fun initToolbar() {
        val toolbar: Toolbar? = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar?.setNavigationIcon(R.mipmap.back)
        toolbar?.setNavigationOnClickListener { onBackPressed() }
        val titleView: TextView? = toolbar?.findViewById(R.id.tv_title)
        titleView?.text = getToolbarTitle()

    }

    open protected fun getToolbarTitle(): String = ""
    protected abstract fun getContentLayout(): Int

    fun showProgress() {
        progress_layout.showLoading()
    }

    fun showError() {
        showError(getString(R.string.loading_error))
    }

    fun showError(errMsg: String) {
        progress_layout.showError(errMsg, { loadData() })
    }

    fun showContent() {
        progress_layout.showContent()
    }
}