package com.ipd.taxiu.presenter.store

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ProductDetailBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.presenter.BasePresenter

class StoreProductDetailPresenter : BasePresenter<StoreProductDetailPresenter.IStoreProductDetailView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun loadProductDetail(productId: Int, formId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().storeProductDetail(GlobalParam.getUserIdOrJump(), productId, formId),
                object : Response<BaseResult<ProductDetailBean>>() {
                    override fun _onNext(result: BaseResult<ProductDetailBean>) {
                        if (result.code == 0) {
                            mView?.loadProductDetailSuccess(result.data)
                        } else {
                            mView?.loadProductDetailFail(result.msg)
                        }

                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadProductDetailFail("连接服务器失败")
                    }
                })
    }


    interface IStoreProductDetailView {
        fun loadProductDetailSuccess(info: ProductDetailBean)

        fun loadProductDetailFail(errMsg: String)

    }
}