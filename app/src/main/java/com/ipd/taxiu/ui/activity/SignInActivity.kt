package com.ipd.taxiu.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ipd.jumpbox.jumpboxlibrary.utils.PopupUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, SignInActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "每日签到"

    override fun getContentLayout(): Int = R.layout.activity_sign_in

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        sign_in_view.setDate(System.currentTimeMillis())
    }

    override fun initListener() {
        tv_sign_in.setOnClickListener {
            val contentView = LayoutInflater.from(mActivity).inflate(R.layout.layout_sign_in_success, null)
            val popupWindow = PopupUtils.getPopup(mActivity, contentView, window)
            contentView.setOnClickListener { popupWindow.dismiss() }
            popupWindow.width = ViewGroup.LayoutParams.WRAP_CONTENT
            popupWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT
            PopupUtils.showViewAtCenter(popupWindow, window, window.decorView, null)
        }
    }

}