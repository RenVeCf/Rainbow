package com.ipd.taxiu.presenter.order

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.OrderDetailBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.presenter.BasePresenter

open class OrderPresenter<T : OrderPresenter.IOrderOperationView> : BasePresenter<T, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun cancelOrder(orderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().orderCancel(GlobalParam.getUserId(), orderId),
                object : Response<BaseResult<OrderDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<OrderDetailBean>) {
                        if (result.code == 0) {
                            mView?.cancelOrderSuccess()
                        } else {
                            mView?.cancelOrderFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.cancelOrderFail("连接服务器失败")
                    }
                })
    }

    fun receivedOrder(orderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().orderReceived(GlobalParam.getUserId(), orderId),
                object : Response<BaseResult<OrderDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<OrderDetailBean>) {
                        if (result.code == 0) {
                            mView?.receivedOrderSuccess()
                        } else {
                            mView?.receivedOrderFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.receivedOrderFail("连接服务器失败")
                    }
                })
    }

    fun deleteOrder(orderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().orderDelete(GlobalParam.getUserId(), orderId),
                object : Response<BaseResult<OrderDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<OrderDetailBean>) {
                        if (result.code == 0) {
                            mView?.deleteOrderSuccess()
                        } else {
                            mView?.deleteOrderFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.deleteOrderFail("连接服务器失败")
                    }
                })
    }

    interface IOrderOperationView {
        fun cancelOrderSuccess()
        fun cancelOrderFail(errMsg: String)
        fun receivedOrderSuccess()
        fun receivedOrderFail(errMsg: String)
        fun deleteOrderSuccess()
        fun deleteOrderFail(errMsg: String)
    }
}