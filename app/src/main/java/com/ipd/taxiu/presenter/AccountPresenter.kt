package com.ipd.taxiu.presenter

import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.LoginBean
import com.ipd.taxiu.bean.RegisterBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.utils.StringUtils

class AccountPresenter<V> : BasePresenter<V, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun login(phone: String, password: String) {
        if (mView !is ILoginView) return
        val view = mView as ILoginView

        if (!CommonUtils.isMobileNO(phone)) {
            view.loginFail("请输入正确的手机号")
            return
        } else if (!CommonUtils.passwordIsLegal(password)) {
            view.loginFail("请输入${Constant.PASSWORD_MIN_LENGHT}至${Constant.PASSWORD_MAX_LENGHT}位密码")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().login(phone, password),
                object : Response<BaseResult<LoginBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<LoginBean>) {
                        if (result.code == 0) {
                            loginSuccess(result.data)
                            view.loginSuccess()
                        } else {
                            view.loginFail(result.msg)
                        }
                    }
                })

    }

    fun phoneLogin(phone: String, code: String) {
        if (mView !is IPhoneLoginView) return
        val view = mView as IPhoneLoginView

        if (!CommonUtils.isMobileNO(phone)) {
            view.loginFail("请输入正确的手机号")
            return
        } else if (code.length < Constant.SMS_CODE_LENGHT) {
            view.loginFail("请输入正确的验证码")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().phoneLogin(code, phone),
                object : Response<BaseResult<LoginBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<LoginBean>) {
                        if (result.code == 0) {
                            loginSuccess(result.data)
                            view.loginSuccess()
                        } else {
                            view.loginFail(result.msg)
                        }
                    }
                })

    }


    fun getSmsCode(phone: String,type:String) {
        if (mView !is BaseSmsCodeView) return
        val view = mView as BaseSmsCodeView

        if (!CommonUtils.isMobileNO(phone)) {
            view.getSmsCodeFail("请输入正确的手机号")
            return
        }

        mModel?.getNormalRequestData(
                when (mView) {
                    is IRegisterView -> ApiManager.getService().registerSmsCode(phone,type)
                    is IPhoneLoginView, is IForgetPasswordView -> ApiManager.getService().phoneLoginSmsCode(phone,type)
                    else -> ApiManager.getService().registerSmsCode(phone,type)
                },
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
        } else if (!CommonUtils.passwordIsLegal(password)) {
            view.registerFail("请输入${Constant.PASSWORD_MIN_LENGHT}至${Constant.PASSWORD_MAX_LENGHT}位密码")
            return
        } else if (code.length < Constant.SMS_CODE_LENGHT) {
            view.registerFail("请输入正确的验证码")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().register(code, phone, password, inviteCode),
                object : Response<BaseResult<RegisterBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<RegisterBean>) {
                        if (result.code == 0) {
                            view.registerSuccess(result.data)
                        } else {
                            view.registerFail(result.msg)
                        }
                    }
                })
    }

    fun findPassword(phone: String, password: String, code: String) {
        if (mView !is IForgetPasswordView) return
        val view = mView as IForgetPasswordView

        if (!CommonUtils.isMobileNO(phone)) {
            view.findFail("请输入正确的手机号")
            return
        } else if (!CommonUtils.passwordIsLegal(password)) {
            view.findFail("请输入${Constant.PASSWORD_MIN_LENGHT}至${Constant.PASSWORD_MAX_LENGHT}位密码")
            return
        } else if (code.length < Constant.SMS_CODE_LENGHT) {
            view.findFail("请输入正确的验证码")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().forgetPassword(code, phone, password),
                object : Response<BaseResult<LoginBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<LoginBean>) {
                        if (result.code == 0) {
                            view.findSuccess()
                        } else {
                            view.findFail(result.msg)
                        }
                    }
                })
    }


    private fun loginSuccess(loginResult: LoginBean) {
        GlobalParam.saveUserId(loginResult.USER_ID.toString())
    }


    interface BaseSmsCodeView {
        fun getSmsCodeSuccess()
        fun getSmsCodeFail(errMsg: String)
    }


    interface ILoginView {
        fun loginSuccess()
        fun loginFail(errMsg: String)
    }

    interface IPhoneLoginView : BaseSmsCodeView {
        fun loginSuccess()
        fun loginFail(errMsg: String)
    }


    interface IRegisterView : BaseSmsCodeView {
        fun registerSuccess(registerInfo: RegisterBean)
        fun registerFail(errMsg: String)
    }

    interface IForgetPasswordView : BaseSmsCodeView {
        fun findSuccess()
        fun findFail(errMsg: String)
    }
}