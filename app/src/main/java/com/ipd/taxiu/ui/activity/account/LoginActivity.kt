package com.ipd.taxiu.ui.activity.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.MainActivity
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getBaseLayout(): Int = R.layout.activity_login

    override fun initView(bundle: Bundle?) {

    }

    override fun loadData() {
    }

    override fun initListener() {
        tv_register.setOnClickListener { RegisterActivity.launch(mActivity) }//注册
        tv_phone_login.setOnClickListener { PhoneLoginActivity.launch(mActivity) }//验证码登录
        tv_forget.setOnClickListener { LostPwdActivity.launch(mActivity) }//忘记密码
        btn_login.setOnClickListener {
            toastShow("登录成功")
            MainActivity.launch(mActivity)
        }
    }

}