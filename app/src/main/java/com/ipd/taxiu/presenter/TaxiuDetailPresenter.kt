package com.ipd.taxiu.presenter.store

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.TaxiuBean
import com.ipd.taxiu.bean.TaxiuDetailBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.presenter.BasePresenter

class TaxiuDetailPresenter : BasePresenter<TaxiuDetailPresenter.ITaxiuDetailView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun loadDetail(taxiuId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().taxiuDetail(GlobalParam.getUserIdOrJump(), taxiuId),
                object : Response<BaseResult<TaxiuDetailBean>>() {
                    override fun _onNext(result: BaseResult<TaxiuDetailBean>) {
                        if (result.code == 0) {
                            mView?.loadDetailSuccess(result.data)
                        } else {
                            mView?.loadDetailFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadDetailFail("连接服务器失败")
                    }
                })
    }


    fun toCollect(taxiuId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().taxiuCollect(GlobalParam.getUserIdOrJump(), "2", taxiuId),
                object : Response<BaseResult<TaxiuDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<TaxiuDetailBean>) {
                        if (result.code == 0) {
                            mView?.collectSuccess()
                        } else {
                            mView?.collectFail(result.msg)
                        }
                    }
                })
    }

    fun delete(taxiuId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().taxiuDelete(GlobalParam.getUserIdOrJump(), taxiuId),
                object : Response<BaseResult<TaxiuBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<TaxiuBean>) {
                        if (result.code == 0) {
                            mView?.deleteSuccess()
                        } else {
                            mView?.deleteFail(result.msg)
                        }
                    }
                })
    }

    interface ITaxiuDetailView {
        fun loadDetailSuccess(detail: TaxiuDetailBean)
        fun loadDetailFail(errMsg: String)
        fun collectSuccess()
        fun collectFail(errMsg: String)
        fun deleteSuccess()
        fun deleteFail(errMsg: String)
    }

}