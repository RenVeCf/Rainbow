package com.ipd.taxiu.ui.activity.coupon

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ExchangeHisBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.http.HttpUrl
import com.ipd.taxiu.presenter.CouponPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.utils.HtmlImageGetter
import kotlinx.android.synthetic.main.activity_exchange_record_detail.*

/**
Created by Miss on 2018/8/10
兑换记录详情
 */
class ExchangeRecordDetailActivity : BaseUIActivity(), CouponPresenter.IExchangeInfoView {
    private var mPresenter: CouponPresenter<CouponPresenter.IExchangeInfoView>? = null

    companion object {
        fun launch(activity: Activity, exchangeId: Int) {
            val intent = Intent(activity, ExchangeRecordDetailActivity::class.java)
            intent.putExtra("exchangeId", exchangeId)
            activity.startActivity(intent)
        }
    }

    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = CouponPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }

    override fun getContentLayout(): Int = R.layout.activity_exchange_record_detail
    override fun getToolbarTitle(): String = "兑换记录详情"

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        var exchangeId = intent.getIntExtra("exchangeId", 0)
        mPresenter?.getExchangeInfo(exchangeId)
    }

    override fun initListener() {
    }

    override fun getInfoSuccess(data: ExchangeHisBean) {
        ImageLoader.loadImgFromLocal(this, HttpUrl.IMAGE_URL + data.LOGO, iv_record_header)
        var category = data.CATEGORY
        if (category == 1) {
            tv_record_title.text = data.SATISFY_PRICE.toString() + "元 单品类优惠券"
        }
        if (category == 2) {
            tv_record_title.text = data.SATISFY_PRICE.toString() + "元 全品类优惠券"
        }
        tv_record_explain.text = "满" + data.PRICE.toString() + "减" + data.SATISFY_PRICE
        tv_record_integral.text = data.SCORE.toString()
        tv_exchange_time.text = "兑换时间："+data.CREATETIME
        tv_instructions.text = Html.fromHtml(data.CONTENT, HtmlImageGetter(this,tv_instructions),null)
    }

    override fun getInfoFail(errMsg: String) {
        toastShow(errMsg)
    }
}