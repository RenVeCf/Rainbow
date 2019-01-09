package com.ipd.rainbow.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom
import com.ipd.rainbow.platform.global.GlobalApplication
import kotlin.properties.Delegates


/**
 * Created by jumpbox on 2017/5/19.
 */
abstract class BaseActivity : AppCompatActivity() {
    protected var mActivity: Activity by Delegates.notNull()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getBaseLayout())
        mActivity = this
        //设置屏幕显示方向
        setActivityScreenOrientation()
        onViewAttach()

        initBaseLayout()
        initView(bundle = savedInstanceState)
        loadData()
        initListener()
    }


    override fun onDestroy() {
        super.onDestroy()
        onViewDetach()
    }

    /**
     * 屏幕显示方向
     */
    open protected fun setActivityScreenOrientation() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }


    protected abstract fun getBaseLayout(): Int
    open protected fun initBaseLayout() {}
    protected open fun onViewAttach() {}
    protected open fun onViewDetach() {}
    protected abstract fun initView(bundle: Bundle?)
    protected abstract fun loadData()
    protected abstract fun initListener()


    fun toastShow(errmsg: String) = toastShow(false, errmsg)

    fun toastShow(success: Boolean, errmsg: String) {
        ToastCommom.getInstance().show(GlobalApplication.mContext, success, errmsg)
    }


}
