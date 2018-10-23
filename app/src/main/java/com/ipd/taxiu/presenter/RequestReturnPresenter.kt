package com.ipd.taxiu.presenter.store

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ReturnOrderInfoBean
import com.ipd.taxiu.bean.ReturnReasonBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.presenter.BasePresenter

class RequestReturnPresenter : BasePresenter<RequestReturnPresenter.IRequestReturnView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun loadReturnOrderInfo(orderId: Int, orderDetailId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().orderReturnInfo(GlobalParam.getUserIdOrJump(), orderId, orderDetailId),
                object : Response<BaseResult<ReturnOrderInfoBean>>() {
                    override fun _onNext(result: BaseResult<ReturnOrderInfoBean>) {
                        if (result.code == 0) {
                            mView?.loadReturnOrderInfoSuccess(result.data)
                        } else {
                            mView?.loadReturnOrderInfoFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadReturnOrderInfoFail("连接服务器失败")
                    }
                })
    }

    fun loadReturnReason() {
        mModel?.getNormalRequestData(ApiManager.getService().orderReturnReason(GlobalParam.getUserIdOrJump(), 1),
                object : Response<BaseResult<List<ReturnReasonBean>>>(mContext, true) {
                    override fun _onNext(result: BaseResult<List<ReturnReasonBean>>) {
                        if (result.code == 0) {
                            mView?.loadReturnReasonSuccess(result.data)
                        } else {
                            mView?.loadReturnReasonFail(result.msg)
                        }
                    }
                })
    }


    fun commitReturnReason(orderId: Int, orderDetailId: Int, applyNum: Int, category: String, reason: String, content: String, pic: String) {
        mModel?.getNormalRequestData(ApiManager.getService().orderRequestReturn(GlobalParam.getUserIdOrJump(), orderId, orderDetailId, applyNum, category, reason, content, pic),
                object : Response<BaseResult<String>>(mContext, true) {
                    override fun _onNext(result: BaseResult<String>) {
                        if (result.code == 0) {
                            mView?.requestReturnSuccess()
                        } else {
                            mView?.requestReturnFail(result.msg)
                        }
                    }
                })
    }


    interface IRequestReturnView {
        fun loadReturnOrderInfoSuccess(info: ReturnOrderInfoBean)
        fun loadReturnOrderInfoFail(errMsg: String)
        fun loadReturnReasonSuccess(list: List<ReturnReasonBean>)
        fun loadReturnReasonFail(errMsg: String)
        fun requestReturnSuccess()
        fun requestReturnFail(errMsg: String)
    }
}