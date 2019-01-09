package com.ipd.rainbow.presenter.order

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.OrderDetailBean
import com.ipd.rainbow.bean.WechatBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response

class OrderDetailPresenter : OrderPresenter<OrderDetailPresenter.IOrderDetailView>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun loadOrderInfo(orderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().orderDetail(GlobalParam.getUserId(), orderId),
                object : Response<BaseResult<OrderDetailBean>>() {
                    override fun _onNext(result: BaseResult<OrderDetailBean>) {
                        if (result.code == 0) {
                            mView?.loadOrderDetailSuccess(result.data)
                        } else {
                            mView?.loadOrderDetailFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadOrderDetailFail("连接服务器失败")
                    }
                })


    }

    fun wechat(orderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().orderWechat(GlobalParam.getUserIdOrJump(), orderId),
                object : Response<BaseResult<WechatBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<WechatBean>) {
                        if (result.code == 0) {
                            mView?.orderWechatSuccess(result.data)
                        } else {
                            mView?.payFail(result.msg)
                        }
                    }
                })
    }

    fun alipay(orderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().orderAlipay(GlobalParam.getUserIdOrJump(), orderId),
                object : Response<BaseResult<String>>(mContext, true) {
                    override fun _onNext(result: BaseResult<String>) {
                        if (result.code == 0) {
                            mView?.orderAlipaySuccess(result.data)
                        } else {
                            mView?.payFail(result.msg)
                        }
                    }
                })
    }

    fun balance(orderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().orderBalance(GlobalParam.getUserIdOrJump(), orderId),
                object : Response<BaseResult<Int>>(mContext, true) {
                    override fun _onNext(result: BaseResult<Int>) {
                        if (result.code == 0) {
                            mView?.orderBalanceSuccess()
                        } else {
                            mView?.payFail(result.msg)
                        }
                    }
                })
    }

    interface IOrderDetailView : OrderPresenter.IOrderOperationView {
        fun loadOrderDetailSuccess(info: OrderDetailBean)
        fun loadOrderDetailFail(errMsg: String)
        fun orderWechatSuccess(wechatInfo: WechatBean)
        fun orderAlipaySuccess(info: String)
        fun orderBalanceSuccess()
        fun payFail(errMsg: String)
    }
}