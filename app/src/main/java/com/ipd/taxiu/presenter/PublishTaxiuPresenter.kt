package com.ipd.taxiu.presenter.store

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.TaxiuLableBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.presenter.BasePresenter

class PublishTaxiuPresenter : BasePresenter<PublishTaxiuPresenter.IPublishTaxiuView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun loadTaxiuLable() {
        mModel?.getNormalRequestData(ApiManager.getService().taxiuLableList(GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<List<TaxiuLableBean>>>() {
                    override fun _onNext(result: BaseResult<List<TaxiuLableBean>>) {
                        if (result.code == 0) {
                            mView?.loadTaxiuLableSuccess(result.data)
                        } else {
                            mView?.loadTaxiuLableFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadTaxiuLableFail("连接服务器失败")
                    }
                })


    }

    fun publishTaxiuImage(content: String, picStr: String, tipId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().publishTaxiu(GlobalParam.getUserIdOrJump(), content,
                "", picStr, tipId.toString(), "2", ""),
                object : Response<BaseResult<TaxiuLableBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<TaxiuLableBean>) {
                        if (result.code == 0) {
                            mView?.publishTaxiuSuccess()
                        } else {
                            mView?.publishTaxiuFail(result.msg)
                        }
                    }
                })
    }


    interface IPublishTaxiuView {
        fun loadTaxiuLableSuccess(lables: List<TaxiuLableBean>)
        fun loadTaxiuLableFail(errMsg: String)
        fun publishTaxiuSuccess()
        fun publishTaxiuFail(errMsg: String)
    }
}