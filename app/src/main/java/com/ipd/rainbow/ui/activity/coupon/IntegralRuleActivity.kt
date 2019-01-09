package com.ipd.rainbow.ui.activity.coupon

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.TextBean
import com.ipd.rainbow.presenter.TextPresenter
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.utils.HtmlImageGetter
import kotlinx.android.synthetic.main.activity_integral_rule.*

/**
Created by Miss on 2018/8/10
积分规则
 */
class IntegralRuleActivity : BaseUIActivity(), TextPresenter.ITextView {
    private var mPresenter: TextPresenter<TextPresenter.ITextView>? = null

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, IntegralRuleActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_integral_rule
    override fun getToolbarTitle(): String = "积分规则"

    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = TextPresenter()
        mPresenter?.attachView(this,this)
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
        mPresenter?.getTextInfo(3)
    }

    override fun initListener() {
    }


    override fun onSuccess(data: TextBean) {
        tv_answer_content.text = Html.fromHtml(data.CONTENT, HtmlImageGetter(this, tv_answer_content), null)
    }

    override fun onFail(errMsg: String) {
        toastShow(errMsg)
    }

}