package com.ipd.taxiu.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

class NotVerticalScrollRecyclerView : RecyclerView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun canScrollVertically(direction: Int): Boolean {
        return false
    }
}