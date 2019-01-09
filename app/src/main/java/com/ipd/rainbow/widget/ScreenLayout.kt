package com.ipd.rainbow.widget

import android.app.Activity
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.ipd.rainbow.R
import kotlinx.android.synthetic.main.layout_product_screen.view.*

class ScreenLayout : ConstraintLayout {
    private var mSortType: ScreenType = ScreenType.NONE
    private var mExpertSortType: ScreenType = ScreenType.NONE

    fun getSortType(): ScreenType = mSortType

    enum class ScreenType(val type: Int) {
        NORMAL_SORT(0),
        COMMENT_SORT(1),
        SALE_SORT(2),
        PRICE_TOP_SORT(3),
        PRICE_BOTTOM_SORT(4),
        EXPERT_SORT(6),
        NONE(5)
    }

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    private fun init() {
    }


    private var disallowClickable = false
    fun disallowClickable() {
        disallowClickable = true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (disallowClickable) {
            return true
        }
        return super.onInterceptTouchEvent(ev)
    }

    private var backgroundView: View? = null
    fun setBackgroupView(view: View) {
        backgroundView = view
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initWidgetByType()

        ll_normal.setOnClickListener {
            SynthesizeScreenView(context as Activity, mSortType, this, backgroundView, { pos, isSelected ->
                when (pos) {
                    0 -> switchSortType(if (isSelected) ScreenType.NORMAL_SORT else ScreenType.NONE)
                    1 -> switchSortType(if (isSelected) ScreenType.COMMENT_SORT else ScreenType.NONE)
                }
            }).showView()
        }

        ll_sales.setOnClickListener {
            when (mSortType) {
                ScreenType.SALE_SORT -> switchSortType(ScreenType.NONE)
                else -> switchSortType(ScreenType.SALE_SORT)
            }
        }
        ll_price.setOnClickListener {
            when (mSortType) {
                ScreenType.PRICE_TOP_SORT -> switchSortType(ScreenType.PRICE_BOTTOM_SORT)
                ScreenType.PRICE_BOTTOM_SORT -> switchSortType(ScreenType.PRICE_TOP_SORT)
                else -> switchSortType(ScreenType.PRICE_TOP_SORT)
            }
        }
        ll_screen.setOnClickListener {
            mExpertScreenOnClick?.invoke(it)
        }
    }

    private var mExpertScreenOnClick: ((view: View) -> Unit)? = null
    fun setExpertScreenClickListener(onClick: (view: View) -> Unit) {
        mExpertScreenOnClick = onClick
    }

    fun onExpertSort() {
        mExpertSortType = ScreenType.EXPERT_SORT
        initWidgetByType()
        mOnSortTypeChangeListener?.onChange(mSortType)
    }

    fun onCancelExpertSort() {
        mExpertSortType = ScreenType.NONE
        initWidgetByType()
        mOnSortTypeChangeListener?.onChange(mSortType)
    }

    private fun switchSortType(sortType: ScreenType) {
        mSortType = sortType
        initWidgetByType()
        mOnSortTypeChangeListener?.onChange(mSortType)
    }


    private fun initWidgetByType() {
        ll_normal.getChildAt(0).isSelected = mSortType == ScreenType.NORMAL_SORT || mSortType == ScreenType.COMMENT_SORT
        (ll_normal.getChildAt(0) as TextView).text = if (mSortType == ScreenType.COMMENT_SORT) "评论" else "综合"
        ll_normal.getChildAt(1).isSelected = mSortType == ScreenType.NORMAL_SORT || mSortType == ScreenType.COMMENT_SORT
        ll_sales.getChildAt(0).isSelected = mSortType == ScreenType.SALE_SORT
        ll_price.getChildAt(0).isSelected = mSortType == ScreenType.PRICE_TOP_SORT || mSortType == ScreenType.PRICE_BOTTOM_SORT
        iv_price_top.setImageResource(if (mSortType == ScreenType.PRICE_TOP_SORT) R.mipmap.arrow_top_primary_dark else R.mipmap.arrow_top_black)
        iv_price_bottom.setImageResource(if (mSortType == ScreenType.PRICE_BOTTOM_SORT) R.mipmap.arrow_bottom_primary_dark else R.mipmap.arrow_bottom_black)
        ll_screen.getChildAt(0).isSelected = mExpertSortType == ScreenType.EXPERT_SORT
        ll_screen.getChildAt(1).isSelected = mExpertSortType == ScreenType.EXPERT_SORT
    }


    fun getCompositeValue(): Int {
        return when (mSortType) {
            ScreenType.NORMAL_SORT -> 1
            ScreenType.COMMENT_SORT -> 2
            else -> 0
        }
    }

    fun getSaleValue(): Int {
        return when (mSortType) {
            ScreenType.SALE_SORT -> 1
            else -> 0
        }
    }

    fun getPriceValue(): Int {
        return when (mSortType) {
            ScreenType.PRICE_TOP_SORT -> 2
            ScreenType.PRICE_BOTTOM_SORT -> 1
            else -> 0
        }
    }

    private var mOnSortTypeChangeListener: OnSortTypeChangeListener? = null
    fun setSortTypeChangeListener(listener: OnSortTypeChangeListener) {
        mOnSortTypeChangeListener = listener
    }


    interface OnSortTypeChangeListener {
        fun onChange(sortType: ScreenType)
    }


}