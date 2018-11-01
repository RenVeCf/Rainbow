package com.ipd.taxiu.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil
import com.ipd.taxiu.R

class CircleMenuLayout : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private val mCircleTextView: TextView by lazy {
        val textView = TextView(context)
        val params = RelativeLayout.LayoutParams(DensityUtil.dip2px(context, 15f), DensityUtil.dip2px(context, 15f))
        params.addRule(ALIGN_PARENT_RIGHT)
        params.addRule(ALIGN_PARENT_TOP)
        params.rightMargin = DensityUtil.dip2px(context, 5f)
        textView.layoutParams = params
        textView.setBackgroundResource(R.drawable.shape_message_bg)
        textView.setTextColor(resources.getColor(R.color.white))
        textView.textSize = 8f
        textView.gravity = Gravity.CENTER
        textView.text = "1"
        textView
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        addView(mCircleTextView)
    }

    fun setCircleNum(num: Int) {
        if (num == 0) {
            mCircleTextView.visibility = View.GONE
            return
        }

        mCircleTextView.text = num.toString()
        mCircleTextView.visibility = View.VISIBLE
    }


}