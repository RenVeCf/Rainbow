package com.ipd.taxiu.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ProductExpertScreenBean
import kotlinx.android.synthetic.main.item_product_model_lable.view.*

class ScreenLableView : ScreenFlowLayout {
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?) : super(context)

    private var mCurCheckedPos = -1

    fun addView(info: ProductExpertScreenBean.ScreenInfo?) {
        addView(info, false)
    }

    fun addView(info: ProductExpertScreenBean.ScreenInfo?, isPrice: Boolean) {
        if (info == null) return
        val childView = LayoutInflater.from(context).inflate(R.layout.item_product_model_lable, this, false)
        if (isPrice) {
            childView.cb_lable.text = "${info.MIN_PRICE}~${info.MAX_PRICE}"
        } else {
            childView.cb_lable.text = info.NAME
        }
        val childPos = childCount
        addView(childView)
        setChecked(childPos, childPos == mCurCheckedPos)
        childView.setOnClickListener {
            if (mCurCheckedPos == childPos) {
                setChecked(mCurCheckedPos, false)
                mCurCheckedPos = -1
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

    fun getCheckedPos() = mCurCheckedPos


}