package com.ipd.rainbow.presenter

import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.rainbow.bean.AddressBean
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.ProvinceBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response

/**
Created by Miss on 2018/8/15
 */
class AddressPresenter<V> : BasePresenter<V, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun addAddress(address: String, city: String, dist: String, prov: String, recipient: String, status: Int, tel: String, user_id: String) {
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
        if (prov == "") {
            view.onAddFail("请选择所在城市")
            return
        }
        if (address == "") {
            view.onAddFail("请输入详细地址")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().addAddress(address, city, dist, prov, recipient, status, tel.toLong(), user_id),
                object : Response<BaseResult<AddressBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<AddressBean>) {
                        if (result.code == 0) {
                            view.onAddSuccess()
                        } else {
                            view.onAddFail(result.msg)
                        }
                    }
                })
    }

    fun getCityList(regionId: String, userId: String) {
        if (mView !is ICityView) return
        val view = mView as ICityView

        mModel?.getNormalRequestData(ApiManager.getService().getListAll(regionId, userId),
                object : Response<BaseResult<ArrayList<ProvinceBean>>>(mContext, true) {
                    override fun _onNext(result: BaseResult<ArrayList<ProvinceBean>>) {
                        if (result.code == 0) {
                            view.getListSuccess(result.data)
                        } else {
                            view.getListFail(result.msg)
                        }
                    }
                })
    }

    fun getAddressInfo(userId: String, addressId: String) {
        if (mView !is IAddressInfoView) return
        val view = mView as IAddressInfoView

        mModel?.getNormalRequestData(ApiManager.getService().getAddressInfo(userId, addressId),
                object : Response<BaseResult<AddressBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<AddressBean>) {
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

        mModel?.getNormalRequestData(ApiManager.getService().addressDelete(userId, addressId),
                object : Response<BaseResult<AddressBean>>(mContext, false) {
                    override fun _onNext(result: BaseResult<AddressBean>) {
                        if (result.code == 0) {
                            view.deleteSuccess()
                        } else {
                            view.deleteFail(result.msg)
                        }
                    }
                })
    }

    fun getAddressUpdate(address:String,city:String,dist:String,prov:String,
                       recipient:String,status:Int,tel:String,userId: String, addressId: String) {
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
        if (prov == "") {
            view.updateFail("请选择所在城市")
            return
        }
        if (address == "") {
            view.updateFail("请输入详细地址")
            return
        }
        mModel?.getNormalRequestData(ApiManager.getService().addressUpdate(address,city,dist,prov,
                recipient,status,tel,userId, addressId),
                object : Response<BaseResult<AddressBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<AddressBean>) {
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

    interface ICityView {
        fun getListSuccess(data: ArrayList<ProvinceBean>)
        fun getListFail(errMsg: String)
    }

    interface IAddressInfoView {
        fun getAddressInfoSuccess(data: AddressBean)
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