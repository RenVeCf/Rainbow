package com.ipd.rainbow.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.layout_taxiu_header.view.*

class CommentSortLayout : LinearLayout {
    companion object {
        val TIME = 1
        val HOT = 2
    }

    var mCurSort = TIME

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onFinishInflate() {
        super.onFinishInflate()
        setOnClickListener {
            if (mCurSort == TIME) {
                mCurSort = HOT
                tv_sort.text = "按热门排序"
            } else {
                mCurSort = TIME
                tv_sort.text = "按时间排序"
            }
            mSortChangeListener?.onChange(mCurSort)
        }
    }

    private var mSortChangeListener: SortChangeListener? = null
    fun setSortChange(sortChangeListener: SortChangeListener) {
        mSortChangeListener = sortChangeListener
    }

    interface SortChangeListener {
        fun onChange(sortType: Int)
    }

}