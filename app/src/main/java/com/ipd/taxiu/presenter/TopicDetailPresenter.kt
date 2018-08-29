package com.ipd.taxiu.presenter.store

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.TaxiuDetailBean
import com.ipd.taxiu.bean.TopicDetailBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.presenter.BasePresenter

class TopicDetailPresenter : BasePresenter<TopicDetailPresenter.ITopicDetailView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun loadDetail(topicId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().topicDetail(GlobalParam.getUserIdOrJump(), topicId),
                object : Response<BaseResult<TopicDetailBean>>() {
                    override fun _onNext(result: BaseResult<TopicDetailBean>) {
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


    fun toCollect(topicId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().taxiuCollect(GlobalParam.getUserIdOrJump(), "4", topicId),
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


    interface ITopicDetailView {
        fun loadDetailSuccess(detail: TopicDetailBean)
        fun loadDetailFail(errMsg: String)
        fun collectSuccess()
        fun collectFail(errMsg: String)
    }
}