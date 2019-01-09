package com.ipd.rainbow.widget

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.animation.Animation
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.activity.SignInActivity
import razerdp.basepopup.BasePopupWindow

class SignInPopup : BasePopupWindow {

    constructor(context: Context?) : super(context) {
        findViewById(R.id.tv_sign_in).setOnClickListener {
            dismiss()
            SignInActivity.launch(context as Activity)
        }
    }

    override fun onCreatePopupView(): View {
        return createPopupById(R.layout.layout_sign_in)
    }

    override fun getClickToDismissView(): View {
        return this.findViewById(R.id.iv_close)
    }

    override fun initShowAnimation(): Animation? = null


    override fun initAnimaView(): View? = null


}