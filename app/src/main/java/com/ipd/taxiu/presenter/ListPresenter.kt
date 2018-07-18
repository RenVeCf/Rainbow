package com.ipd.taxiu.presenter

import android.text.TextUtils
import com.ipd.taxiu.model.ListModel
import com.ipd.taxiu.platform.http.Response
import rx.Observable

/**
 * Created by jumpbox on 2017/5/24.
 */
class ListPresenter<D> : BasePresenter<ListPresenter.IListView<D>, ListModel<D>>() {
    override fun initModel() {
        mModel = ListModel()
    }

    /**
     * 加载列表数据
     * 1.第一次加载数据，先显示进度页面
     * 2.请求数据
     * 3.获取数据是否成功
     * 1).成功
     * 1.判断是否数据为空
     * 2.判断是否没有更多数据
     * 3.第一次加载：隐藏进度页面，显示内容区域
     * 4.如果是刷新，重置data集合，并刷新adapter
     * 5.如果没有更多数据并且当前是下拉加载状态中，禁止下拉加载更多
     * 2).失败
     * 1.第一次加载显示错误页面

     * @param isRefresh
     */
    fun loadListData(observable: Observable<D>, isRefresh: Boolean, isCreate: Boolean) {
        mView?.checkNeedShowProgress()
        mModel?.getListData(observable, object : Response<D>(mContext) {
            override fun _onNext(result: D) {
                val isSuccessResult: String = mView?.getDataSuccess(result) ?: "加载失败..."
                if (TextUtils.isEmpty(isSuccessResult)) {
                    mView?.loadListDataSuccess(isRefresh, result)
                } else {
                    if (isCreate || isRefresh) {
                        mView?.firstLoadListDataError(isRefresh, isSuccessResult)
                    } else {
                        mView?.loadListDataError(isSuccessResult)
                    }
                }

            }

            override fun onError(e: Throwable?) {
                onCompleted()
                if (isCreate || isRefresh) {
                    mView?.firstLoadListDataError(isRefresh, "连接服务器失败...")
                } else {
                    mView?.loadListDataError("连接服务器失败...")
                }

            }

            override fun onCompleted() {
                super.onCompleted()
                mView?.loadListDataComplete()

            }

        })

    }

    interface IListView<T> {
        fun checkNeedShowProgress()
        fun getDataSuccess(result: T): String?
        fun loadListDataSuccess(isRefresh: Boolean, result: T)
        fun firstLoadListDataError(isRrefresh: Boolean, msg: String)
        fun loadListDataComplete()
        fun loadListDataError(errMsg: String)
    }


}
