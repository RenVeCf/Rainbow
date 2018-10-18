package com.ipd.taxiu.presenter.order

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.OrderDetailBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.presenter.BasePresenter

class OrderDetailPresenter : BasePresenter<OrderDetailPresenter.IOrderDetailView, BasicModel>() {
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

    interface IOrderDetailView {
        fun loadOrderDetailSuccess(info: OrderDetailBean)
        fun loadOrderDetailFail(errMsg: String)
    }
}