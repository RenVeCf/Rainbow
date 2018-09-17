package com.ipd.taxiu.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.Animation
import android.widget.TextView
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.StoreGiftCouponAdapter
import com.ipd.taxiu.bean.ExchangeHisBean
import razerdp.basepopup.BasePopupWindow

class StoreGiftGetSuccessPopup : BasePopupWindow {

    constructor(context: Context, list: List<ExchangeHisBean>) : super(context) {
        val giftCouponNumView = findViewById(R.id.tv_gift_coupon_num) as TextView
        val couponRecyclerView = findViewById(R.id.coupon_recycler_view) as RecyclerView
        giftCouponNumView.text = String.format(context.resources.getString(R.string.store_gift_coupon_num), list.size)
        couponRecyclerView.adapter = StoreGiftCouponAdapter(context, list)
    }

    override fun onCreatePopupView(): View {
        return createPopupById(R.layout.layout_store_gift_get_success)
    }

    override fun getClickToDismissView(): View {
        return this.findViewById(R.id.iv_close)
    }

    override fun initShowAnimation(): Animation? = null


    override fun initAnimaView(): View? = null


}