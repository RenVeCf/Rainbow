package com.ipd.taxiu.presenter

import android.content.Context
import com.ipd.taxiu.model.BaseModel

/**
 * Created by jumpbox on 2017/5/24.
 */
abstract class BasePresenter<T, M : BaseModel> {
    var mView: T? = null//view层
    var mModel: M? = null//model层
    var mContext: Context? = null

    open fun attachView(context: Context, t: T) {
        if (mModel != null) {
            throw RuntimeException("model出现二次初始化.....")
        }
        if (mView != null) {
            throw RuntimeException("view出现二次初始化.....")
        }
        mView = t
        mContext = context
        initModel()
    }

    open fun detachView() {
        mView = null
        mModel = null
        mContext = null
    }

    abstract fun initModel()
}