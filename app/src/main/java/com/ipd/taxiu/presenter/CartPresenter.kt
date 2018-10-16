package com.ipd.taxiu.presenter.store

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.CartProductBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.presenter.BasePresenter

class CartPresenter : BasePresenter<CartPresenter.ICartView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun changeCartNum(cartId: Int, num: Int, callback: (isSuccess: Boolean) -> Unit) {
        mModel?.getNormalRequestData(ApiManager.getService().cartChange(GlobalParam.getUserId(), num, cartId),
                object : Response<BaseResult<String>>() {
                    override fun _onNext(result: BaseResult<String>) {
                        callback.invoke(result.code == 0)
                    }
                })


    }

    fun deleteCartProduct(pos: Int,cartProductBean: CartProductBean) {
        mModel?.getNormalRequestData(ApiManager.getService().cartDelete(GlobalParam.getUserId(), cartProductBean.CART_ID),
                object : Response<BaseResult<String>>(mContext,true) {
                    override fun _onNext(result: BaseResult<String>) {
                        if (result.code == 0) {
                            mView?.cartDeleteSuccess(pos,cartProductBean)
                        } else {
                            mView?.cartDeleteFail(result.msg)
                        }

                    }
                })
    }

    interface ICartView {
        fun cartDeleteSuccess(pos: Int,cartProductBean: CartProductBean)
        fun cartDeleteFail(errMsg: String)
    }
}