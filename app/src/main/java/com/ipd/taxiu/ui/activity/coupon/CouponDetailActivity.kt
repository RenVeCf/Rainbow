package com.ipd.taxiu.ui.activity.coupon

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ExchangeBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.http.HttpUrl
import com.ipd.taxiu.presenter.CouponPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.utils.HtmlImageGetter
import com.ipd.taxiu.widget.MessageDialog
import kotlinx.android.synthetic.main.activity_coupon_detail.*

/**
Created by Miss on 2018/8/10
 */
class CouponDetailActivity : BaseUIActivity(), CouponPresenter.ICouponInfoView, CouponPresenter.IToExchangeView {
    private var couponId = 0
    private var mPresenter: CouponPresenter<CouponPresenter.ICouponInfoView>? = null
    private var mToExchangePresenter: CouponPresenter<CouponPresenter.IToExchangeView>? = null

    companion object {
        fun launch(activity: Activity, couponId: Int) {
            val intent = Intent(activity, CouponDetailActivity::class.java)
            intent.putExtra("couponId", couponId)
            activity.startActivity(intent)
        }
    }

    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = CouponPresenter()
        mPresenter?.attachView(this, this)

        mToExchangePresenter = CouponPresenter()
        mToExchangePresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null

        mToExchangePresenter?.detachView()
        mToExchangePresenter = null
    }

    override fun getContentLayout(): Int = R.layout.activity_coupon_detail
    override fun getToolbarTitle(): String = "优惠券详情"

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        couponId = intent.getIntExtra("couponId", 0)
        mPresenter?.getCouponInfo(couponId)
    }

    override fun initListener() {
        btn_exchange.setOnClickListener { initMessageDialog() }
    }

    private fun initMessageDialog() {
        val builder = MessageDialog.Builder(this)
        builder.setTitle("确认要兑换该优惠券吗？")
        builder.setMessage("兑换后发放至我的-优惠券列表中")
        builder.setCommit("确认兑换", { builder ->
            mToExchangePresenter?.toExchange(couponId)
            builder.dialog.dismiss()
        })
        builder.setCancel("暂不兑换", { builder ->
            builder.dialog.dismiss()
        })
        builder.dialog.show()

    }

    override fun getInfoSuccess(data: ExchangeBean) {
        val category = data.CATEGORY
        if (category == 1) {
            tv_coupon_name.text = data.SATISFY_PRICE.toString() + "元 单品类优惠券"
        }
        if (category == 2) {
            tv_coupon_name.text = data.SATISFY_PRICE.toString() + "元 全品类优惠券"
        }
        tv_integral_num.text = data.SCORE.toString()
        tv_instructions.text = Html.fromHtml(data.CONTENT, HtmlImageGetter(this, tv_instructions), null)
        ImageLoader.loadImgFromLocal(this, HttpUrl.IMAGE_URL + data.LOGO, iv_coupon_detail)
    }

    override fun getInfoFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun toExchangeMsg(msg: String) {
        toastShow(msg)
    }

}