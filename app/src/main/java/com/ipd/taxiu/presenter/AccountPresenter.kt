package com.ipd.taxiu.presenter

import android.app.Activity
import android.text.TextUtils
import cn.jpush.android.api.JPushInterface
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.wechat.friends.Wechat
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils
import com.ipd.jumpbox.jumpboxlibrary.utils.ThreadUtils
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.LoginBean
import com.ipd.taxiu.bean.RegisterBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalApplication
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.utils.AlipayUtils
import java.util.*


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

    fun thirdLogin(type: Int, openId: String, logo: String, nickname: String) {
        if (mView !is ILoginView) return
        val view = mView as ILoginView

        if (TextUtils.isEmpty(openId)) {
            view.loginFail("openId为空")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().thirdLogin(openId, type.toString()),
                object : Response<BaseResult<LoginBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<LoginBean>) {
                        when {
                            result.code == 0 -> {
                                //登录成功
                                loginSuccess(result.data)
                                view.loginSuccess()
                            }
                            result.code == 20000 -> {
                                //账号绑定
                                view.thirdNeedBinding(type, openId, logo, nickname)
                            }
                            else -> view.loginFail(result.msg)
                        }
                    }
                })

    }

    fun qqLogin() {
        if (mView !is ILoginView) return
        val view = mView as ILoginView

        val plat = ShareSDK.getPlatform(QQ.NAME)
        plat.removeAccount(true) //移除授权状态和本地缓存，下次授权会重新授权
        plat.SSOSetting(false) //SSO授权，传false默认是客户端授权，没有客户端授权或者不支持客户端授权会跳web授权
        plat.platformActionListener = object : PlatformActionListener {
            override fun onComplete(p0: Platform, p1: Int, p2: HashMap<String, Any>?) {
                ThreadUtils.runOnUIThread {
                    val token = p0.db.token
                    val userLogo = p0.db.userIcon
                    val username = p0.db.userName
                    view.thirdAuthSuccess(2, token, userLogo, username)
                }
            }

            override fun onCancel(p0: Platform?, p1: Int) {
                ThreadUtils.runOnUIThread {
                    view.thirdAuthCancel()
                }
            }

            override fun onError(p0: Platform?, p1: Int, p2: Throwable?) {
                ThreadUtils.runOnUIThread {
                    view.thirdAuthError("授权失败")
                }
            }

        }
        plat.showUser(null)

    }

    fun wechatLogin() {
        if (mView !is ILoginView) return
        val view = mView as ILoginView

        val wechat = ShareSDK.getPlatform(Wechat.NAME)
        wechat.removeAccount(true) //移除授权状态和本地缓存，下次授权会重新授权
        wechat.platformActionListener = object : PlatformActionListener {
            override fun onComplete(p0: Platform, p1: Int, p2: HashMap<String, Any>) {
                LogUtils.e("tag", p0.db.exportData())
                LogUtils.e("tag", p2.toString())
                ThreadUtils.runOnUIThread {
                    val token = p2["unionid"].toString()
                    val userLogo = p0.db.userIcon
                    val username = p0.db.userName
                    view.thirdAuthSuccess(1, token, userLogo, username)
                }
            }

            override fun onCancel(p0: Platform?, p1: Int) {
                ThreadUtils.runOnUIThread {
                    view.thirdAuthCancel()
                }
            }

            override fun onError(p0: Platform?, p1: Int, p2: Throwable?) {
                ThreadUtils.runOnUIThread {
                    view.thirdAuthError("授权失败")
                }
            }

        }
        wechat.showUser(null)

    }

    fun alipayLogin(activity: Activity) {
        if (mView !is ILoginView) return
        val view = mView as ILoginView

        AlipayUtils.getInstance().authV2(activity, object : AlipayUtils.OnAuthListener {
            override fun onAuthSuccess(authId: String) {
                view.thirdAuthSuccess(3, authId, "", "")
            }

            override fun onAuthFail() {
                view.thirdAuthError("授权失败")
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

    fun bindingPhone(type: Int, phone: String, code: String, openId: String, logo: String, nickname: String) {
        if (mView !is IBindingPhoneView) return
        val view = mView as IBindingPhoneView

        if (!CommonUtils.isMobileNO(phone)) {
            view.bindingFail("请输入正确的手机号")
            return
        } else if (code.length < Constant.SMS_CODE_LENGHT) {
            view.bindingFail("请输入正确的验证码")
            return
        } else if (TextUtils.isEmpty(openId)) {
            view.bindingFail("openId为空")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().bindingPhone(phone, code, type, openId, logo, nickname),
                object : Response<BaseResult<LoginBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<LoginBean>) {
                        if (result.code == 0) {
                            loginSuccess(result.data)
                            view.bindingSuccess()
                        } else {
                            view.bindingFail(result.msg)
                        }
                    }
                })

    }

    fun bindingPhoneSkipUser(phone: String, code: String) {
        if (mView !is IBindingPhoneView) return
        val view = mView as IBindingPhoneView

        if (!CommonUtils.isMobileNO(phone)) {
            view.bindingFail("请输入正确的手机号")
            return
        } else if (code.length < Constant.SMS_CODE_LENGHT) {
            view.bindingFail("请输入正确的验证码")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().BindingPhoneSkipUser(GlobalParam.getUserIdOrJump(), phone, code),
                object : Response<BaseResult<LoginBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<LoginBean>) {
                        if (result.code == 0) {
                            loginSuccess(result.data)
                            view.bindingSkipUserSuccess()
                        } else {
                            view.bindingFail(result.msg)
                        }
                    }
                })

    }

    fun skipBindPhone(type: Int, openId: String, logo: String, nickname: String) {
        if (mView !is IBindingPhoneView) return
        val view = mView as IBindingPhoneView

        if (TextUtils.isEmpty(openId)) {
            view.bindingFail("openId为空")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().skipBindingPhone(type, openId, logo, nickname),
                object : Response<BaseResult<LoginBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<LoginBean>) {
                        if (result.code == 0) {
                            loginSuccess(result.data)
                            view.bindingSuccess()
                        } else {
                            view.bindingFail(result.msg)
                        }
                    }
                })

    }


    fun getSmsCode(phone: String, type: String) {
        if (mView !is BaseSmsCodeView) return
        val view = mView as BaseSmsCodeView

        if (!CommonUtils.isMobileNO(phone)) {
            view.getSmsCodeFail("请输入正确的手机号")
            return
        }

        mModel?.getNormalRequestData(
                when (mView) {
                    is IRegisterView -> ApiManager.getService().registerSmsCode(phone, type)
                    is IPhoneLoginView, is IForgetPasswordView -> ApiManager.getService().phoneLoginSmsCode(phone, type)
                    is IBindingPhoneView -> ApiManager.getService().bindingPhoneSmsCode(phone, type)
                    else -> ApiManager.getService().registerSmsCode(phone, type)
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
        JPushInterface.setAlias(GlobalApplication.mContext, loginResult.USER_ID, "TX_${loginResult.USER_ID}")
        GlobalParam.saveUserId(loginResult.USER_ID.toString())
    }


    interface BaseSmsCodeView {
        fun getSmsCodeSuccess()
        fun getSmsCodeFail(errMsg: String)
    }


    interface ILoginView {
        fun loginSuccess()
        fun loginFail(errMsg: String)
        fun thirdAuthCancel()
        fun thirdAuthError(errMsg: String)
        fun thirdAuthSuccess(type: Int, openId: String, logo: String, nickname: String)
        fun thirdNeedBinding(type: Int, openId: String, logo: String, nickname: String)
    }

    interface IPhoneLoginView : BaseSmsCodeView {
        fun loginSuccess()
        fun loginFail(errMsg: String)
    }

    interface IBindingPhoneView : BaseSmsCodeView {
        fun bindingSuccess()
        fun bindingFail(errMsg: String)
        fun bindingSkipUserSuccess()
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