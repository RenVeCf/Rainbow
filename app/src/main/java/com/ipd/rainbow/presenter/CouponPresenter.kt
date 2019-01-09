package com.ipd.rainbow.presenter

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.ExchangeBean
import com.ipd.rainbow.bean.ExchangeHisBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response

/**
Created by Miss on 2018/9/3
 */
class CouponPresenter<V> : BasePresenter<V, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun getCouponInfo(couponId: Int) {
        if (mView !is ICouponInfoView) return
        val view = mView as ICouponInfoView

        mModel?.getNormalRequestData(ApiManager.getService().scoreCouponInfo(couponId, GlobalParam.getUserId()),
                object : Response<BaseResult<ExchangeBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<ExchangeBean>?) {
                        if (result!!.code == 0) {
                            view.getInfoSuccess(result.data)
                        } else {
                            view.getInfoFail(result.msg)
                        }
                    }

                })
    }

    fun toExchange(couponId: Int) {
        if (mView !is IToExchangeView) return
        val view = mView as IToExchangeView

        mModel?.getNormalRequestData(ApiManager.getService().toExchange(couponId, GlobalParam.getUserId()),
                object : Response<BaseResult<ExchangeBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<ExchangeBean>) {
                        view.toExchangeMsg(result.code == 0, result.msg)
                    }

                })
    }

    fun getExchangeInfo(exchangeId: Int) {
        if (mView !is IExchangeInfoView) return
        val view = mView as IExchangeInfoView

        mModel?.getNormalRequestData(ApiManager.getService().exchangeInfo(exchangeId, GlobalParam.getUserId()),
                object : Response<BaseResult<ExchangeHisBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<ExchangeHisBean>?) {
                        if (result!!.code == 0) {
                            view.getInfoSuccess(result.data)
                        } else {
                            view.getInfoFail(result.msg)
                        }
                    }

                })
    }

    interface ICouponInfoView {
        fun getInfoSuccess(data: ExchangeBean)
        fun getInfoFail(errMsg: String)
    }

    interface IToExchangeView {
        fun toExchangeMsg(success: Boolean, msg: String)
    }

    interface IExchangeInfoView {
        fun getInfoSuccess(data: ExchangeHisBean)
        fun getInfoFail(errMsg: String)
    }
}