package com.ipd.taxiu.widget

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.animation.Animation
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.activity.SignInActivity
import razerdp.basepopup.BasePopupWindow

class ProductBrandScreenPopup : BasePopupWindow {

    constructor(context: Context?) : super(context) {
        findViewById(R.id.tv_sign_in).setOnClickListener {
            dismiss()
            SignInActivity.launch(context as Activity)
        }
    }

    override fun onCreatePopupView(): View {
        return createPopupById(R.layout.layout_product_brand_screen)
    }

    override fun getClickToDismissView(): View? {
        return null
    }

    override fun initShowAnimation(): Animation? = null


    override fun initAnimaView(): View? = null


}