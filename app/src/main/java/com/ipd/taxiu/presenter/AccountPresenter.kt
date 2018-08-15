package com.ipd.taxiu.presenter

import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response

class AccountPresenter<V> : BasePresenter<V, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun getSmsCode(phone: String) {
        if (mView !is BaseSmsCodeView) return
        val view = mView as BaseSmsCodeView

        if (!CommonUtils.isMobileNO(phone)) {
            view.getSmsCodeFail("请输入正确的手机号")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().registerSmsCode(phone),
                object : Response<BaseResult<String>>(mContext, true) {
                    override fun _onNext(result: BaseResult<String>) {
                        if (result.code == 0) {
                            view.getSmsCodeSuccess()
                        } else {
                            view.getSmsCodeFail(result.msg)
                        }
                    }
                })
    }

    fun register(phone: String, password: String, code: String, inviteCode: String) {
        if (mView !is IRegisterView) return
        val view = mView as IRegisterView

        if (!CommonUtils.isMobileNO(phone)) {
            view.registerFail("请输入正确的手机号")
            return
        } else if (password.length < Constant.PASSWORD_MIN_LENGHT || password.length > Constant.PASSWORD_MAX_LENGHT) {
            view.registerFail("请输入${Constant.PASSWORD_MIN_LENGHT}至${Constant.PASSWORD_MAX_LENGHT}位密码")
            return
        } else if (code.length < Constant.SMS_CODE_LENGHT) {
            view.registerFail("请输入正确的验证码")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().register(code, phone, password, inviteCode),
                object : Response<BaseResult<String>>(mContext, true) {
                    override fun _onNext(result: BaseResult<String>) {
                        if (result.code == 0) {
                            view.registerSuccess()
                        } else {
                            view.registerFail(result.msg)
                        }
                    }
                })
    }


    interface BaseSmsCodeView {
        fun getSmsCodeSuccess()
        fun getSmsCodeFail(errMsg: String)
    }

    interface IRegisterView : BaseSmsCodeView {
        fun registerSuccess()
        fun registerFail(errMsg: String)
    }
}