package com.ipd.rainbow.widget

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.widget.TextView
import com.ipd.rainbow.R
import razerdp.basepopup.BasePopupWindow

class SignInSuccessPopup : BasePopupWindow {

    constructor(context: Context?) : super(context)

    fun setScore(score: Int): SignInSuccessPopup {
        (findViewById(R.id.tv_score) as TextView).text = String.format(context.resources.getString(R.string.take_it_score), score)
        return this
    }

    override fun onCreatePopupView(): View {
        return createPopupById(R.layout.layout_sign_in_success)
    }

    override fun getClickToDismissView(): View {
        return this.findViewById(R.id.rl_content)
    }

    override fun initShowAnimation(): Animation? = null


    override fun initAnimaView(): View? = null


}