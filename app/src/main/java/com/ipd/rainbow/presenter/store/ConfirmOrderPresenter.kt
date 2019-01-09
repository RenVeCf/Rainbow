package com.ipd.rainbow.presenter.store

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.CartCashBean
import com.ipd.rainbow.bean.PayResult
import com.ipd.rainbow.bean.WechatBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.presenter.BasePresenter
import com.ipd.rainbow.widget.ChoosePayTypeLayout

class ConfirmOrderPresenter : BasePresenter<ConfirmOrderPresenter.IConfirmOrderView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun cartCash(cartIds: String, isCart: Int, num: Int, productId: Int, formId: Int, isGroup: Boolean) {
        mModel?.getNormalRequestData(ApiManager.getService().cartCash(GlobalParam.getUserIdOrJump(), cartIds, 0, 0, 0, isCart, num, productId, formId, if (isGroup) 1 else 0),
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

    fun spellCash(activityId: Int, num: Int, productId: Int, formId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().spellCash(GlobalParam.getUserIdOrJump(), 0, 0, 0, num, activityId, productId, formId),
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
        if (payWay == ChoosePayTypeLayout.PayType.WECHAT) {
            mModel?.getNormalRequestData(ApiManager.getService().cartConfirmWechat(GlobalParam.getUserIdOrJump(), cartIds, addressId, invoiceHead, invoiceNo, invoiceType, payWay, useCoupon, couponId, isCart, num, productId, formId),
                    object : Response<PayResult<WechatBean>>(mContext, true) {
                        override fun _onNext(result: PayResult<WechatBean>) {
                            if (result.code == 0) {
                                mView?.confirmOrderSuccess(payWay, null, result)
                            } else {
                                mView?.confirmOrderFail(result.msg)
                            }
                        }
                    })
        } else {
            mModel?.getNormalRequestData(ApiManager.getService().cartConfirm(GlobalParam.getUserIdOrJump(), cartIds, addressId, invoiceHead, invoiceNo, invoiceType, payWay, useCoupon, couponId, isCart, num, productId, formId),
                    object : Response<PayResult<String>>(mContext, true) {
                        override fun _onNext(result: PayResult<String>) {
                            if (result.code == 0) {
                                mView?.confirmOrderSuccess(payWay, result, null)
                            } else {
                                mView?.confirmOrderFail(result.msg)
                            }
                        }
                    })
        }


    }

    fun spellConfirmOrder(activityId: Int, num: Int, productId: Int, formId: Int, addressId: String, invoiceHead: String, invoiceNo: String, invoiceType: Int, payWay: Int, useCoupon: Int, couponId: Int) {
        if (payWay == ChoosePayTypeLayout.PayType.WECHAT) {
            mModel?.getNormalRequestData(ApiManager.getService().spellConfirmWechat(GlobalParam.getUserIdOrJump(), activityId, addressId, invoiceHead, invoiceNo, invoiceType, payWay, useCoupon, couponId, num, productId, formId),
                    object : Response<PayResult<WechatBean>>(mContext, true) {
                        override fun _onNext(result: PayResult<WechatBean>) {
                            if (result.code == 0) {
                                mView?.confirmOrderSuccess(payWay, null, result)
                            } else {
                                mView?.confirmOrderFail(result.msg)
                            }
                        }
                    })
        } else {
            mModel?.getNormalRequestData(ApiManager.getService().spellConfirm(GlobalParam.getUserIdOrJump(), activityId, addressId, invoiceHead, invoiceNo, invoiceType, payWay, useCoupon, couponId, num, productId, formId),
                    object : Response<PayResult<String>>(mContext, true) {
                        override fun _onNext(result: PayResult<String>) {
                            if (result.code == 0) {
                                mView?.confirmOrderSuccess(payWay, result, null)
                            } else {
                                mView?.confirmOrderFail(result.msg)
                            }
                        }
                    })
        }


    }

    interface IConfirmOrderView {
        fun loadCartCashSuccess(info: CartCashBean)
        fun loadCartCashFail(errMsg: String)
        fun confirmOrderSuccess(payType: Int, payResult: PayResult<String>?, wechatPayResult: PayResult<WechatBean>?)
        fun confirmOrderFail(errMsg: String)
    }
}