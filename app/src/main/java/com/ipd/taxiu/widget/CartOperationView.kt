package com.ipd.taxiu.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ipd.taxiu.R
import kotlinx.android.synthetic.main.layout_cart_operation.view.*

class CartOperationView : FrameLayout {
    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    val mContentView by lazy { LayoutInflater.from(context).inflate(R.layout.layout_cart_operation, this, false) }

    private fun init() {
        addView(mContentView)

        mContentView.rl_sub.setOnClickListener {
            var num = getNum()
            if (num <= 1) {
                return@setOnClickListener
            } else {
                num -= 1
            }
            mContentView.tv_num.text = num.toString()
            checkOperationStatus()
        }

        mContentView.rl_add.setOnClickListener {
            var num = getNum()
            num += 1
            mContentView.tv_num.text = num.toString()
            checkOperationStatus()
        }
        checkOperationStatus()
    }

    fun getNum(): Int {
        val numStr = mContentView.tv_num.text.toString().trim()
        return numStr.toInt()
    }

    private fun checkOperationStatus() {
        mContentView.iv_sub.isEnabled = getNum() > 1
    }


}