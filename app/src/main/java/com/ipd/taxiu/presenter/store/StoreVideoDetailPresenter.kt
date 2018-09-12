package com.ipd.taxiu.presenter.store

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.StoreVideoDetailBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.presenter.BasePresenter

class StoreVideoDetailPresenter : BasePresenter<StoreVideoDetailPresenter.IStoreVideoDetailView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun loadVideoDetail(videoId: String) {
        mModel?.getNormalRequestData(ApiManager.getService().storeVideoDetail(videoId, GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<StoreVideoDetailBean>>() {
                    override fun _onNext(result: BaseResult<StoreVideoDetailBean>) {
                        if (result.code == 0) {
                            mView?.loadVideoDetailSuccess(result.data)
                        } else {
                            mView?.loadVideoDetailFail(result.msg)
                        }

                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadVideoDetailFail("连接服务器失败")
                    }
                })
    }


    interface IStoreVideoDetailView {
        fun loadVideoDetailSuccess(list: StoreVideoDetailBean)

        fun loadVideoDetailFail(errMsg: String)

    }
}