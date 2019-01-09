package com.ipd.rainbow.presenter.order

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.GroupOrderDetailBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.presenter.BasePresenter

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