package com.ipd.rainbow.presenter

import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.OrderPeopleBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response

/**
Created by Miss on 2018/8/15
 */
class OrderPeoplePresenter<V> : BasePresenter<V, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun addAddress(recipient: String, status: Int, tel: String, cardNumber: String, user_id: String) {
        if (mView !is IAddAddressView) return
        val view = mView as IAddAddressView
        if (recipient == "") {
            view.onAddFail("请输入收件人")
            return
        }
        if (!CommonUtils.isMobileNO(tel)) {
            view.onAddFail("请输入正确的手机号码")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().addOrderPeople(recipient, status, tel.toLong(), cardNumber, user_id),
                object : Response<BaseResult<OrderPeopleBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<OrderPeopleBean>) {
                        if (result.code == 0) {
                            view.onAddSuccess()
                        } else {
                            view.onAddFail(result.msg)
                        }
                    }
                })
    }

    fun getAddressInfo(userId: String, addressId: String) {
        if (mView !is IAddressInfoView) return
        val view = mView as IAddressInfoView

        mModel?.getNormalRequestData(ApiManager.getService().getOrderPeopleInfo(userId, addressId),
                object : Response<BaseResult<OrderPeopleBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<OrderPeopleBean>) {
                        if (result.code == 0) {
                            view.getAddressInfoSuccess(result.data)
                        } else {
                            view.getAddressInfoFail(result.msg)
                        }
                    }
                })
    }

    fun deleteAddress(userId: String, addressId: String) {
        if (mView !is IAddressDeleteView) return
        val view = mView as IAddressDeleteView

        mModel?.getNormalRequestData(ApiManager.getService().orderPeopleDelete(userId, addressId),
                object : Response<BaseResult<OrderPeopleBean>>(mContext, false) {
                    override fun _onNext(result: BaseResult<OrderPeopleBean>) {
                        if (result.code == 0) {
                            view.deleteSuccess()
                        } else {
                            view.deleteFail(result.msg)
                        }
                    }
                })
    }

    fun getAddressUpdate(recipient: String, status: Int, tel: String, cardNumber: String, userId: String, addressId: String) {
        if (mView !is IAddressUpdateView) return
        val view = mView as IAddressUpdateView
        if (recipient == "") {
            view.updateFail("请输入收件人")
            return
        }
        if (!CommonUtils.isMobileNO(tel)) {
            view.updateFail("请输入正确的手机号码")
            return
        }
        mModel?.getNormalRequestData(ApiManager.getService().orderPeopleUpdate(recipient, status, tel, cardNumber, userId, addressId),
                object : Response<BaseResult<OrderPeopleBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<OrderPeopleBean>) {
                        if (result.code == 0) {
                            view.updateSuccess()
                        } else {
                            view.updateFail(result.msg)
                        }
                    }
                })
    }

    interface IAddAddressView {
        fun onAddSuccess()
        fun onAddFail(errMsg: String)
    }

    interface IAddressInfoView {
        fun getAddressInfoSuccess(data: OrderPeopleBean)
        fun getAddressInfoFail(errMsg: String)
    }

    interface IAddressUpdateView {
        fun updateSuccess()
        fun updateFail(errMsg: String)
    }

    interface IAddressDeleteView {
        fun deleteSuccess()
        fun deleteFail(errMsg: String)
    }
}