package com.ipd.rainbow.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil
import com.ipd.rainbow.R

class CircleMenuLayout : RelativeLayout {
    private var mRightMargin = DensityUtil.dip2px(context, 8f)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleMenuLayout, defStyleAttr, 0)
        mRightMargin = a.getDimensionPixelSize(R.styleable.CircleMenuLayout_circleRightMargin, 0)
        a.recycle()
    }


    private val mCircleTextView: TextView by lazy {
        val textView = TextView(context)
        val params = RelativeLayout.LayoutParams(DensityUtil.dip2px(context, 15f), DensityUtil.dip2px(context, 15f))
        params.addRule(ALIGN_PARENT_RIGHT)
        params.addRule(ALIGN_PARENT_TOP)
        params.rightMargin = mRightMargin
        textView.layoutParams = params
        textView.setBackgroundResource(R.drawable.shape_message_bg)
        textView.setTextColor(resources.getColor(R.color.white))
        textView.textSize = 8f
        textView.gravity = Gravity.CENTER
        textView.visibility = View.GONE
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