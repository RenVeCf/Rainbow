package com.ipd.rainbow.presenter

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.PayResult
import com.ipd.rainbow.bean.UpdatePwdBean
import com.ipd.rainbow.bean.WechatBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.widget.ChoosePayTypeLayout

class VipPresenter : BasePresenter<VipPresenter.IVipView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun closeAutoPay() {
        mModel?.getNormalRequestData(ApiManager.getService().closeVipAutoPay(GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<UpdatePwdBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<UpdatePwdBean>) {
                        if (result.code == 0) {
                            mView?.closeAutoPaySuccess()
                        } else {
                            mView?.closeAutoPayFail(result.msg)
                        }
                    }
                }
        )
    }

    fun confirmOrder(level: Int, type: Int, isAutoPay: Int, payWay: Int) {
        if (payWay == ChoosePayTypeLayout.PayType.WECHAT) {
            mModel?.getNormalRequestData(ApiManager.getService().rechargeVipWechat(GlobalParam.getUserIdOrJump(), level, type, isAutoPay, payWay),
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
            mModel?.getNormalRequestData(ApiManager.getService().rechargeVip(GlobalParam.getUserIdOrJump(), level, type, isAutoPay, payWay),
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

    interface IVipView {
        fun confirmOrderSuccess(payType: Int, payResult: PayResult<String>?, wechatPayResult: PayResult<WechatBean>?)
        fun confirmOrderFail(errMsg: String)
        fun closeAutoPaySuccess()
        fun closeAutoPayFail(errMsg: String)
    }
}