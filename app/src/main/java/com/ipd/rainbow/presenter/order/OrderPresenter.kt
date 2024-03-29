package com.ipd.rainbow.presenter.order

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.OrderDetailBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.presenter.BasePresenter

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

    fun buyAgain(orderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().orderBuyAgain(GlobalParam.getUserId(), orderId),
                object : Response<BaseResult<OrderDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<OrderDetailBean>) {
                        if (result.code == 0) {
                            mView?.buyAgainSuccess()
                        } else {
                            mView?.buyAgainFail(result.msg)
                        }
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
        fun buyAgainSuccess()
        fun buyAgainFail(errMsg: String)
    }
}