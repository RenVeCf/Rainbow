package com.ipd.taxiu.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ProductEvaluateLableBean
import kotlinx.android.synthetic.main.item_product_model_lable.view.*

class ProductEvaluateView : FlowLayout {
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?) : super(context)

    private var mCurCheckedPos = 0


    fun addView(modelInfo: ProductEvaluateLableBean) {
        val childView = LayoutInflater.from(context).inflate(R.layout.item_product_evaluate_lable, this, false) as TextView
        childView.text = modelInfo.name + modelInfo.num
        val childPos = childCount
        addView(childView)
        setChecked(childPos, childPos == mCurCheckedPos)
        childView.setOnClickListener {
            if (childPos == mCurCheckedPos) return@setOnClickListener
            setChecked(mCurCheckedPos, false)
            mCurCheckedPos = childPos
            setChecked(mCurCheckedPos, true)

            mOnCheckedChangeListener?.onChange(modelInfo)
        }

    }

    private fun setChecked(childPos: Int, isChecked: Boolean) {
        if (childPos >= childCount) return
        val childView = getChildAt(childPos)
        childView.cb_lable.isSelected = isChecked
    }

    fun getCheckedPos(): Int = mCurCheckedPos

    private var mOnCheckedChangeListener: OnCheckedChangeListener? = null
    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener) {
        mOnCheckedChangeListener = listener
    }

    interface OnCheckedChangeListener {
        fun onChange(modelInfo: ProductEvaluateLableBean)
    }

}