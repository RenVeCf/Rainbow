package com.ipd.rainbow.ui

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.platform.global.GlobalApplication
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom
import kotlin.properties.Delegates

/**
 * Created by jumpbox on 2017/5/24.
 */
abstract class BaseFragment : Fragment() {

    protected var mActivity: Activity by Delegates.notNull()
    protected var mRootView: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater?.inflate(getBaseLayout(), container, false) as ViewGroup
            initBaseLayout(mRootView)
            onViewAttach()
            initView(savedInstanceState)
            loadData()
            initListener()
        }
        return mRootView
    }


//    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        onViewAttach()
//        loadData()
//        initListener()
//
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        onViewDetach()
//    }

    override fun onDestroy() {
        super.onDestroy()
        onViewDetach()
    }

    protected abstract fun getBaseLayout(): Int//返回页面基布局
    open protected fun initBaseLayout(rootView: ViewGroup?) {

    }

    open protected fun onViewAttach() {}
    open protected fun onViewDetach() {}
    protected abstract fun initView(bundle: Bundle?)
    protected abstract fun loadData()
    protected abstract fun initListener()


    fun toastShow(errmsg: String) = toastShow(false, errmsg)

    fun toastShow(success: Boolean, errmsg: String) {
        ToastCommom.getInstance().show(GlobalApplication.mContext, success, errmsg)
    }

}