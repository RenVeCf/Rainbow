package com.ipd.taxiu.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ProductModelResult
import kotlinx.android.synthetic.main.item_product_model_lable.view.*

class ProductModelView : FlowLayout {
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?) : super(context)

    private var mCurCheckedPos = -1


    fun addView(modelInfo: ProductModelResult.ProductModelBean) {
        val childView = LayoutInflater.from(context).inflate(R.layout.item_product_model_lable, this, false) as TextView
        childView.text = modelInfo.TASTE
        val childPos = childCount

        if (modelInfo.IS_CHOSEN == 1) {
            mCurCheckedPos = childPos
            mOnCheckedChangeListener?.onChange(modelInfo)
        }

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
        fun onChange(modelInfo: ProductModelResult.ProductModelBean)
    }

}