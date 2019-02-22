package com.ipd.rainbow.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductExpertScreenBean
import kotlinx.android.synthetic.main.item_product_model_lable.view.*

class ScreenLableView : ScreenFlowLayout {
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?) : super(context)


    fun addView(info: ProductExpertScreenBean) {
        if (info == null) return
        val childView = LayoutInflater.from(context).inflate(R.layout.item_product_model_lable, this, false)
        childView.cb_lable.text = info.COMMON_NAME
        val childPos = childCount
        addView(childView)
        setCheckedLable(childPos, info.isChecked)
        childView.setOnClickListener {
            info.isChecked = !info.isChecked
            setCheckedLable(childPos,info.isChecked)
            onChange?.invoke(info)
        }
    }

    private fun setCheckedLable(childPos: Int, isChecked: Boolean) {
        if (childPos == -1 || childPos >= childCount) return
        val childView = getChildAt(childPos)
        childView.cb_lable.isSelected = isChecked
    }

    private var onChange: ((ProductExpertScreenBean?) -> Unit)? = null
    fun setCheckedChangeListener(onChange: (ProductExpertScreenBean?) -> Unit) {
        this.onChange = onChange
    }

}