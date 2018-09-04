package com.ipd.taxiu.presenter

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ExchangeBean
import com.ipd.taxiu.bean.ExchangeHisBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response

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
                    override fun _onNext(result: BaseResult<ExchangeBean>?) {
                        view.toExchangeMsg(result!!.msg)
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
        fun toExchangeMsg(msg: String)
    }

    interface IExchangeInfoView {
        fun getInfoSuccess(data: ExchangeHisBean)
        fun getInfoFail(errMsg: String)
    }
}