package com.ipd.taxiu.ui.activity.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.presenter.AccountPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.utils.TimeCountHelper
import kotlinx.android.synthetic.main.activity_lost_pwd.*

class LostPwdActivity : BaseUIActivity(), TextWatcher, AccountPresenter.IForgetPasswordView {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, LostPwdActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "忘记密码"

    override fun getContentLayout(): Int = R.layout.activity_lost_pwd


    private var mPresenter: AccountPresenter<AccountPresenter.IForgetPasswordView>? = null
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
            mPresenter?.getSmsCode(phone)
        }

        btn_find.setOnClickListener {
            val phone = et_phone.text.toString().trim()
            val password = et_password.text.toString().trim()
            val code = et_sms.text.toString().trim()
            mPresenter?.findPassword(phone, password, code)
        }

    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val phone = et_phone.text.toString().trim()
        val sms = et_sms.text.toString().trim()
        val password = et_password.text.toString().trim()
        btn_find.isEnabled = CommonUtils.isMobileNO(phone) && sms.length >= 4 && CommonUtils.passwordIsLegal(password)
    }


    private fun initCodeBtn() {
        tv_get_sms.isEnabled = true
        tv_get_sms.text = "获取验证码"
    }

    private var mTimeCountHelper: TimeCountHelper? = null
    override fun getSmsCodeSuccess() {
        toastShow(true, "已发送验证码到您的手机")
        tv_get_sms.isEnabled = false

        if (mTimeCountHelper == null) {
            mTimeCountHelper = TimeCountHelper.newInstance().setTimeCountListener(
                    object : TimeCountHelper.TimeCountListener {
                        override fun onChange(aLong: Long) {
                            tv_get_sms.text = "${aLong}秒"
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

    override fun findSuccess() {
        toastShow(true, "密码修改成功")
        finish()
    }

    override fun findFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun onDestroy() {
        super.onDestroy()
        mTimeCountHelper?.release()
    }

}