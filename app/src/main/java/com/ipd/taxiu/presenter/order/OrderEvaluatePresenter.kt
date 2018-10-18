package com.ipd.taxiu.presenter.order

import com.google.gson.Gson
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.bean.UploadProductEvaluateBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.presenter.BasePresenter

open class OrderEvaluatePresenter : BasePresenter<OrderEvaluatePresenter.IOrderEvaluateView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun loadEvaluateProductList(orderId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().orderEvaluateProductList(GlobalParam.getUserId(), orderId),
                object : Response<BaseResult<List<ProductBean>>>() {
                    override fun _onNext(result: BaseResult<List<ProductBean>>) {
                        if (result.code == 0) {
                            mView?.loadEvaluateProductListSuccess(result.data)
                        } else {
                            mView?.loadEvaluateProductListFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadEvaluateProductListFail("连接服务器失败")
                    }
                })
    }

    fun publishEvaluate(orderId: Int, list: List<UploadProductEvaluateBean>, descScore: Int, serviceScore: Int, expressScore: Int) {
        val evaluateJson = Gson().toJson(list)
        mModel?.getNormalRequestData(ApiManager.getService().orderPublishEvaluate(GlobalParam.getUserId(), orderId, evaluateJson, descScore, serviceScore, expressScore),
                object : Response<BaseResult<ProductBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<ProductBean>) {
                        if (result.code == 0) {
                            mView?.publishEvaluateSuccess()
                        } else {
                            mView?.publishEvaluateFail(result.msg)
                        }
                    }

                })
    }


    interface IOrderEvaluateView {
        fun loadEvaluateProductListSuccess(list: List<ProductBean>)
        fun loadEvaluateProductListFail(errMsg: String)
        fun publishEvaluateSuccess()
        fun publishEvaluateFail(errMsg: String)
    }
}