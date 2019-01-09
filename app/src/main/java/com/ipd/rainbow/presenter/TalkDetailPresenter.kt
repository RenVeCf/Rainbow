package com.ipd.rainbow.presenter.store

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.TalkBean
import com.ipd.rainbow.bean.TalkDetailBean
import com.ipd.rainbow.bean.TaxiuDetailBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.presenter.BasePresenter

class TalkDetailPresenter : BasePresenter<TalkDetailPresenter.ITalkDetailView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun loadDetail(talkId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().talkDetail(GlobalParam.getUserIdOrJump(), talkId),
                object : Response<BaseResult<TalkDetailBean>>() {
                    override fun _onNext(result: BaseResult<TalkDetailBean>) {
                        if (result.code == 0) {
                            mView?.loadDetailSuccess(result.data)
                        } else {
                            mView?.loadDetailFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadDetailFail("连接服务器失败")
                    }
                })
    }


    fun toCollect(talkId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().taxiuCollect(GlobalParam.getUserIdOrJump(), "5", talkId),
                object : Response<BaseResult<TaxiuDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<TaxiuDetailBean>) {
                        if (result.code == 0) {
                            mView?.collectSuccess()
                        } else {
                            mView?.collectFail(result.msg)
                        }
                    }
                })
    }

    fun delete(talkId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().talkDelete(GlobalParam.getUserIdOrJump(), talkId),
                object : Response<BaseResult<TalkBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<TalkBean>) {
                        if (result.code == 0) {
                            mView?.deleteSuccess()
                        } else {
                            mView?.deleteFail(result.msg)
                        }
                    }
                })
    }


    interface ITalkDetailView {
        fun loadDetailSuccess(detail: TalkDetailBean)
        fun loadDetailFail(errMsg: String)
        fun collectSuccess()
        fun collectFail(errMsg: String)
        fun deleteSuccess()
        fun deleteFail(errMsg: String)
    }
}