package com.ipd.rainbow.ui.activity.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.rainbow.MainActivity
import com.ipd.rainbow.R
import com.ipd.rainbow.presenter.AccountPresenter
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.utils.TimeCountHelper
import kotlinx.android.synthetic.main.activity_phone_login.*

class PhoneLoginActivity : BaseUIActivity(), AccountPresenter.IPhoneLoginView, TextWatcher {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, PhoneLoginActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "手机验证码登录"

    override fun getContentLayout(): Int = R.layout.activity_phone_login


    private var mPresenter: AccountPresenter<AccountPresenter.IPhoneLoginView>? = null
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
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
        et_phone.addTextChangedListener(this)
        et_sms.addTextChangedListener(this)

        tv_get_sms.setOnClickListener {
            val phone = et_phone.text.toString().trim()
            mPresenter?.getSmsCode(phone,"3")
        }
        tv_voice_code.setOnClickListener {
            val phone = et_phone.text.toString().trim()
            mPresenter?.getSmsCode(phone,"3")
        }
        btn_login.setOnClickListener {
            val phone = et_phone.text.toString().trim()
            val code = et_sms.text.toString().trim()
            mPresenter?.phoneLogin(phone, code)
        }

    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val phone = et_phone.text.toString().trim()
        val sms = et_sms.text.toString().trim()
        btn_login.isEnabled = CommonUtils.isMobileNO(phone) && sms.length >= 4
    }

    private fun initCodeBtn() {
        tv_get_sms.isEnabled = true
        tv_get_sms.text = "获取验证码"
        tv_voice_code.isEnabled = true
        tv_voice_code.text = "语音验证码"
    }

    private var mTimeCountHelper: TimeCountHelper? = null
    override fun getSmsCodeSuccess() {
        toastShow(true, "已发送验证码到您的手机")
        tv_get_sms.isEnabled = false
        tv_voice_code.isEnabled = false

        if (mTimeCountHelper == null) {
            mTimeCountHelper = TimeCountHelper.newInstance().setTimeCountListener(
                    object : TimeCountHelper.TimeCountListener {
                        override fun onChange(aLong: Long) {
                            tv_get_sms.text = "${aLong}秒"
                            tv_voice_code.text = "${aLong}秒"
                        }

                        override fun onFinish() {
                            initCodeBtn()
                        }
                    }
            )
        }

        mTimeCountHelper?.start()
    }

    override fun getSmsCodeFail(errMsg: String) {
        initCodeBtn()
        toastShow(errMsg)
    }

    override fun loginSuccess() {
        toastShow(true,"登录成功")
        val intent = Intent(mActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun loginFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun onDestroy() {
        super.onDestroy()
        mTimeCountHelper?.release()
    }

}