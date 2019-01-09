package com.ipd.rainbow.presenter.store

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.CartProductBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.presenter.BasePresenter

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