package com.ipd.rainbow.ui.activity.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import cn.jpush.android.api.JPushInterface
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils
import com.ipd.rainbow.MainActivity
import com.ipd.rainbow.R
import com.ipd.rainbow.platform.global.GlobalApplication
import com.ipd.rainbow.presenter.AccountPresenter
import com.ipd.rainbow.ui.BaseActivity
import com.ipd.rainbow.utils.AlipayUtils
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity(), AccountPresenter.ILoginView, TextWatcher {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getBaseLayout(): Int = R.layout.activity_login


    private var mPresenter: AccountPresenter<AccountPresenter.ILoginView>? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = AccountPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }


    override fun initView(bundle: Bundle?) {
        JPushInterface.deleteAlias(GlobalApplication.mContext, 0)
    }

    override fun loadData() {
    }

    override fun initListener() {
        et_phone.addTextChangedListener(this)
        et_password.addTextChangedListener(this)

        tv_register.setOnClickListener { RegisterActivity.launch(mActivity) }//注册
        tv_phone_login.setOnClickListener { PhoneLoginActivity.launch(mActivity) }//验证码登录
        tv_forget.setOnClickListener { LostPwdActivity.launch(mActivity) }//忘记密码
        btn_login.setOnClickListener {
            val phone = et_phone.text.toString().trim()
            val password = et_password.text.toString().trim()
            mPresenter?.login(phone, password)
        }

        iv_qq.setOnClickListener {
            mPresenter?.qqLogin()
        }
        iv_wechat.setOnClickListener {
            mPresenter?.wechatLogin()
        }
        iv_alipay.setOnClickListener {
            mPresenter?.alipayLogin(mActivity)
        }
    }

    override fun thirdAuthSuccess(type: Int, token: String, userLogo: String, username: String) {
        LogUtils.e("tag", "$token-$userLogo-$username")
        mPresenter?.thirdLogin(type, token, userLogo, username)
    }

    override fun thirdNeedBinding(type: Int, openId: String, logo: String, nickname: String) {
        BindingPhoneActivity.launch(mActivity, type, openId, logo, nickname)
    }

    override fun thirdAuthCancel() {
        toastShow("取消授权")
    }

    override fun thirdAuthError(errMsg: String) {
        toastShow(errMsg)
    }

    override fun loginSuccess() {
        toastShow(true, "登录成功")
        MainActivity.launch(mActivity)
        finish()
    }

    override fun loginFail(errMsg: String) {
        toastShow(errMsg)
    }


    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val phone = et_phone.text.toString().trim()
        val password = et_password.text.toString().trim()
        btn_login.isEnabled = CommonUtils.isMobileNO(phone) &&
                CommonUtils.passwordIsLegal(password)
    }

    override fun onDestroy() {
        super.onDestroy()
        AlipayUtils.getInstance().release()
    }

}