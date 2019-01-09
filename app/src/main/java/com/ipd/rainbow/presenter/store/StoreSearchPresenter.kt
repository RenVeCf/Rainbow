package com.ipd.rainbow.presenter.store

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.ProductDetailBean
import com.ipd.rainbow.bean.StoreSearchHistroyBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.presenter.BasePresenter

class StoreSearchPresenter : BasePresenter<StoreSearchPresenter.IStoreSearchView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun loadSearchHistory() {
        mModel?.getNormalRequestData(ApiManager.getService().storeSearchHistory(GlobalParam.getUserIdOrJump()),
                object : Response<StoreSearchHistroyBean>() {
                    override fun _onNext(result: StoreSearchHistroyBean) {
                        if (result.code == 0) {
                            mView?.loadSearchHistorySuccess(result)
                        } else {
                            mView?.loadSearchHistoryFail(result.msg)
                        }
                    }
                    override fun onError(e: Throwable?) {
                        loadSearchHistory()
                    }
                })
    }

    fun clearSearchHistory() {
        mModel?.getNormalRequestData(ApiManager.getService().storeClearSearchHistory(GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<ProductDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<ProductDetailBean>) {
                        if (result.code == 0) {
                            mView?.clearSearchHisSuccess()
                        } else {
                            mView?.clearSearchHisFail(result.msg)
                        }

                    }
                })
    }


    interface IStoreSearchView {
        fun loadSearchHistorySuccess(info: StoreSearchHistroyBean)
        fun loadSearchHistoryFail(errMsg: String)
        fun clearSearchHisSuccess()
        fun clearSearchHisFail(errMsg: String)

    }
}