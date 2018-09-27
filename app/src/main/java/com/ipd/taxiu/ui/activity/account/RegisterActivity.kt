package com.ipd.taxiu.ui.activity.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.RegisterBean
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.presenter.AccountPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.utils.TimeCountHelper
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseUIActivity(), AccountPresenter.IRegisterView, TextWatcher {
    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val phone = et_phone.text.toString().trim()
        val code = et_sms.text.toString().trim()
        val password = et_password.text.toString().trim()
        btn_register.isEnabled = CommonUtils.isMobileNO(phone) &&
                CommonUtils.passwordIsLegal(password) &&
                code.length >= Constant.SMS_CODE_LENGHT
    }

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, RegisterActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "注册"

    override fun getContentLayout(): Int = R.layout.activity_register

    private var mPresenter: AccountPresenter<AccountPresenter.IRegisterView>? = null
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
        et_password.addTextChangedListener(this)

        tv_get_sms.setOnClickListener {
            val phone = et_phone.text.toString().trim()
            mPresenter?.getSmsCode(phone, "1")
        }
        tv_voice_code.setOnClickListener {
            //语音验证码
            val phone = et_phone.text.toString().trim()
            mPresenter?.getSmsCode(phone, "2")
        }


        btn_register.setOnClickListener {
            if (!cb_user_agent.isChecked) {
                toastShow("请先同意用户注册协议");return@setOnClickListener
            }
            val phone = et_phone.text.toString().trim()
            val code = et_sms.text.toString().trim()
            val password = et_password.text.toString().trim()
            val inviteCode = et_invite_code.text.toString().trim()
            mPresenter?.register(phone, password, code, inviteCode)
        }


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

    override fun registerSuccess(registerInfo: RegisterBean) {
        toastShow(true, "注册成功")
        PetStageActivity.launch(mActivity, registerInfo.USER_ID.toString())
        finish()
    }

    override fun registerFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun onDestroy() {
        super.onDestroy()
        mTimeCountHelper?.release()
    }

}