package com.ipd.rainbow.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class SingleChoiceLayout : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private var mCurCheckedPos = 0
//    override fun onFinishInflate() {
//        super.onFinishInflate()
//
//        if (childCount == 0) return
//        if (mCurCheckedPos >= childCount) mCurCheckedPos = 0
//
//        for (index in 0 until childCount) {
//            val childView = getChildAt(index)
//            childView.isSelected = index == mCurCheckedPos
//
//            childView.setOnClickListener {
//                val lastCheckedView = getChildAt(mCurCheckedPos)
//                lastCheckedView.isSelected = false
//                childView.isSelected = true
//            }
//
//        }
//    }

    override fun addView(childView: View) {
        super.addView(childView)
        val childPos = childCount - 1

        childView.isSelected = childPos == mCurCheckedPos

        childView.setOnClickListener {
            val lastCheckedView = getChildAt(mCurCheckedPos)
            lastCheckedView.isSelected = false

            mCurCheckedPos = childPos
            childView.isSelected = true
        }


    }

    fun getCheckedPos() = mCurCheckedPos

}