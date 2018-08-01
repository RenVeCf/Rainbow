package com.ipd.taxiu.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.ipd.taxiu.R
import kotlinx.android.synthetic.main.item_product_model_lable.view.*

class TaxiuLableView : FlowLayout {
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?) : super(context)

    private var mCurCheckedPos = -1


    fun addView() {
        val childView = LayoutInflater.from(context).inflate(R.layout.item_product_model_lable, this, false)
        val childPos = childCount
        addView(childView)
        setChecked(childPos, childPos == mCurCheckedPos)
        childView.setOnClickListener {
            if (mCurCheckedPos == childPos) {
                setChecked(mCurCheckedPos, false)
                return@setOnClickListener
            }
            setChecked(mCurCheckedPos, false)
            mCurCheckedPos = childPos
            setChecked(mCurCheckedPos, true)
        }

    }

    private fun setChecked(childPos: Int, isChecked: Boolean) {
        if (childPos == -1 || childPos >= childCount) return
        val childView = getChildAt(childPos)
        childView.cb_lable.isSelected = isChecked
    }


}