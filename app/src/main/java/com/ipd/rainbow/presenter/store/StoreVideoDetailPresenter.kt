package com.ipd.rainbow.presenter.store

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.StoreVideoDetailBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.presenter.BasePresenter

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