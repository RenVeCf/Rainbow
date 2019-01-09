package com.ipd.rainbow.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.TaxiuLableBean
import kotlinx.android.synthetic.main.item_product_model_lable.view.*

class TaxiuLableView : FlowLayout {
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?) : super(context)

    //    private var mCurCheckedPos = -1
    private var mCurCheckedPos = ArrayList<Int>()

    fun addView(info: TaxiuLableBean) {
        val childView = LayoutInflater.from(context).inflate(R.layout.item_product_model_lable, this, false)
        childView.cb_lable.text = info.TIP
        val childPos = childCount
        addView(childView)

        if (mCurCheckedPos.contains(childPos)){
            setChecked(childPos, true)
        }else{
            setChecked(childPos, false)
        }

        childView.setOnClickListener {
            //            if (mCurCheckedPos == childPos) {
//                return@setOnClickListener
//            }
//            setChecked(mCurCheckedPos, false)
//            mCurCheckedPos = childPos
//            setChecked(mCurCheckedPos, true)

            if (mCurCheckedPos.contains(childPos)) {
                //已选择，取消选择
                mCurCheckedPos.remove(childPos)
                setChecked(childPos, false)
            } else {
                //未选择
                if (mCurCheckedPos.size >= 3) {
                    return@setOnClickListener
                }
                mCurCheckedPos.add(childPos)
                setChecked(childPos, true)
            }
        }
    }

    private fun setChecked(childPos: Int, isChecked: Boolean) {
        if (childPos == -1 || childPos >= childCount) return
        val childView = getChildAt(childPos)
        childView.cb_lable.isSelected = isChecked
    }

    fun getCheckedPos() = mCurCheckedPos


}