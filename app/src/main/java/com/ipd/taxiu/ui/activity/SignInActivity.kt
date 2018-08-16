package com.ipd.taxiu.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ipd.jumpbox.jumpboxlibrary.utils.PopupUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.SignInBean
import com.ipd.taxiu.presenter.store.SignInPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.activity.coupon.MyIntegralActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.layout_sign_in_success.view.*

class SignInActivity : BaseUIActivity(), SignInPresenter.ISignInView {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, SignInActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "每日签到"

    override fun getContentLayout(): Int = R.layout.activity_sign_in


    private var mPresenter: SignInPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = SignInPresenter()
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
        showProgress()
        mPresenter?.loadSignInInfo()
    }

    override fun initListener() {
        tv_sign_in.setOnClickListener {
            mPresenter?.signIn()
        }

        tv_integral.setOnClickListener {
            val intent = Intent(this, MyIntegralActivity::class.java)
            startActivity(intent)
        }
    }

    override fun loadSignInInfoSuccess(signInBean: SignInBean) {
        showContent()

        tv_integral.text = signInBean.signInfo.USER_SCORE.toString()
        tv_continue_sign.text = signInBean.signInfo.SIGN_DAY.toString()

        //是否已签到
        tv_sign_in.isEnabled = signInBean.signInfo.STATUS == 0
        tv_sign_in.text = if (signInBean.signInfo.STATUS == 0) "签到领积分" else "今日已签到"

        sign_in_view.setDate(System.currentTimeMillis(),signInBean.signInDayList)
    }

    override fun loadSignInInfoFail(errMsg: String) {
        showError(errMsg)
    }

    override fun signInSuccess(score: Int) {
        val contentView = LayoutInflater.from(mActivity).inflate(R.layout.layout_sign_in_success, null)
        contentView.tv_score.text = "获得${score}积分"
        val popupWindow = PopupUtils.getPopup(mActivity, contentView, window)
        contentView.setOnClickListener { popupWindow.dismiss() }
        popupWindow.width = ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT
        PopupUtils.showViewAtCenter(popupWindow, window, window.decorView, null)

        mPresenter?.loadSignInInfo()

    }

    override fun signInFail(errMsg: String) {
        toastShow(errMsg)
    }

}