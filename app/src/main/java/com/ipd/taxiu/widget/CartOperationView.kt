package com.ipd.taxiu.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ipd.taxiu.R
import kotlinx.android.synthetic.main.layout_cart_operation.view.*

class CartOperationView : FrameLayout {
    private var maxNum = 999

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
            mOnCartNumChangeListener?.onNumChange(mContentView.tv_num.text.toString().toInt(), num)
            mContentView.tv_num.text = num.toString()
            checkOperationStatus()
        }

        mContentView.rl_add.setOnClickListener {
            var num = getNum()
            num += 1
            mOnCartNumChangeListener?.onNumChange(mContentView.tv_num.text.toString().toInt(), num)
            mContentView.tv_num.text = num.toString()
            checkOperationStatus()
        }
        checkOperationStatus()
    }

    fun getNum(): Int {
        val numStr = mContentView.tv_num.text.toString().trim()
        return numStr.toInt()
    }

    fun setNum(num: Int) {
        mContentView.tv_num.text = num.toString()
        checkOperationStatus()
    }

    fun setMaxNum(maxNum: Int) {
        this.maxNum = maxNum
        checkOperationStatus()
    }

    private fun checkOperationStatus() {
        mContentView.iv_sub.isEnabled = getNum() > 1
        mContentView.iv_add.isEnabled = getNum() >= maxNum
    }


    private var mOnCartNumChangeListener: OnCartNumChangeListener? = null
    fun setOnCartNumChangeListener(listener: OnCartNumChangeListener) {
        mOnCartNumChangeListener = listener
    }

    fun setEnable(enable: Boolean) {
        mContentView.rl_sub.isEnabled = enable
        mContentView.rl_add.isEnabled = enable
    }

    interface OnCartNumChangeListener {
        fun onNumChange(lastNum: Int, num: Int)
    }

}