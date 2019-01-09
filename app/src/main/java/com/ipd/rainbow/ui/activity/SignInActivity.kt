package com.ipd.rainbow.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.SignInBean
import com.ipd.rainbow.presenter.store.SignInPresenter
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.activity.coupon.MyIntegralActivity
import com.ipd.rainbow.widget.SignInSuccessPopup
import kotlinx.android.synthetic.main.activity_sign_in.*

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

        cl_integral_content.setOnClickListener {
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

        sign_in_view.setDate(System.currentTimeMillis(), signInBean.signInDayList)
    }

    override fun loadSignInInfoFail(errMsg: String) {
        showError(errMsg)
    }

    override fun signInSuccess(score: Int) {
        SignInSuccessPopup(mActivity).setScore(score).showPopupWindow()

        mPresenter?.loadSignInInfo()

    }

    override fun signInFail(errMsg: String) {
        toastShow(errMsg)
    }

}