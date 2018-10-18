package com.ipd.taxiu.presenter.store

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.CartCashBean
import com.ipd.taxiu.event.UpdateCartEvent
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.presenter.BasePresenter
import org.greenrobot.eventbus.EventBus

class ConfirmOrderPresenter : BasePresenter<ConfirmOrderPresenter.IConfirmOrderView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun cartCash(cartIds: String, isCart: Int, num: Int, productId: Int, formId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().cartCash(GlobalParam.getUserIdOrJump(), cartIds, 0, 0, 0, isCart, num, productId, formId),
                object : Response<BaseResult<CartCashBean>>() {
                    override fun _onNext(result: BaseResult<CartCashBean>) {
                        if (result.code == 0) {
//                            EventBus.getDefault().post(UpdateCartEvent()) 暂不需要更新购物车
                            mView?.loadCartCashSuccess(result.data)
                        } else {
                            mView?.loadCartCashFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadCartCashFail("连接服务器失败")

                    }
                })


    }

    fun confirmOrder(cartIds: String, isCart: Int, num: Int, productId: Int, formId: Int, addressId: String, invoiceHead: String, invoiceNo: String, invoiceType: Int, payWay: Int, useCoupon: Int, couponId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().cartConfirm(GlobalParam.getUserIdOrJump(), cartIds, addressId, invoiceHead, invoiceNo, invoiceType, payWay, useCoupon, couponId, isCart, num, productId, formId),
                object : Response<BaseResult<Int>>(mContext,true) {
                    override fun _onNext(result: BaseResult<Int>) {
                        if (result.code == 0) {
                            EventBus.getDefault().post(UpdateCartEvent())
                            mView?.confirmOrderSuccess()
                        } else {
                            mView?.confirmOrderFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.confirmOrderFail("连接服务器失败")

                    }
                })


    }

    interface IConfirmOrderView {
        fun loadCartCashSuccess(info: CartCashBean)
        fun loadCartCashFail(errMsg: String)
        fun confirmOrderSuccess()
        fun confirmOrderFail(errMsg: String)
    }
}