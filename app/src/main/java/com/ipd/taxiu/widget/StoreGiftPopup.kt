package com.ipd.taxiu.widget

import android.content.Context
import android.view.View
import android.view.animation.Animation
import com.ipd.taxiu.R
import razerdp.basepopup.BasePopupWindow

class StoreGiftPopup : BasePopupWindow {

    constructor(context: Context?, takeItMethod: (popup: StoreGiftPopup) -> Unit) : super(context) {
        findViewById(R.id.tv_take_it).setOnClickListener {
            //领取礼包
            takeItMethod.invoke(this)
        }
    }

    override fun onCreatePopupView(): View {
        return createPopupById(R.layout.layout_store_gift)
    }

    override fun getClickToDismissView(): View {
        return this.findViewById(R.id.iv_close)
    }

    override fun initShowAnimation(): Animation? = null


    override fun initAnimaView(): View? = null


}