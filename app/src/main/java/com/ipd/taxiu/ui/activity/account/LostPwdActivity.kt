package com.ipd.taxiu.ui.activity.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_lost_pwd.*

class LostPwdActivity : BaseUIActivity(), TextWatcher {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, LostPwdActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "忘记密码"

    override fun getContentLayout(): Int = R.layout.activity_lost_pwd

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
        et_phone.addTextChangedListener(this)
        et_sms.addTextChangedListener(this)
        et_password.addTextChangedListener(this)

        btn_find.setOnClickListener {
            toastShow(true, "密码修改成功")
            finish()
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

}