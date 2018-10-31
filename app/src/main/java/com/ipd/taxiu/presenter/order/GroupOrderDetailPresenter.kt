package com.ipd.taxiu.presenter.order

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.GroupOrderDetailBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.presenter.BasePresenter

class GroupOrderDetailPresenter : BasePresenter<GroupOrderDetailPresenter.IGroupOrderDetailView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun loadOrderInfo(orderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().spellOrderDetail(GlobalParam.getUserId(), orderId),
                object : Response<BaseResult<GroupOrderDetailBean>>() {
                    override fun _onNext(result: BaseResult<GroupOrderDetailBean>) {
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


    interface IGroupOrderDetailView {
        fun loadOrderDetailSuccess(info: GroupOrderDetailBean)
        fun loadOrderDetailFail(errMsg: String)
    }
}