package com.ipd.taxiu.widget

import android.app.Activity
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.ipd.taxiu.R
import kotlinx.android.synthetic.main.layout_product_screen.view.*

class ScreenLayout : ConstraintLayout {
    private var mSortType: ScreenType = ScreenType.NORMAL_SORT

    fun getSortType(): ScreenType = mSortType

    enum class ScreenType(val type: Int) {
        NORMAL_SORT(0),
        COMMENT_SORT(1),
        SALE_SORT(2),
        PRICE_TOP_SORT(3),
        PRICE_BOTTOM_SORT(4),
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
            switchSortType(ScreenType.SALE_SORT)
        }
        ll_price.setOnClickListener {
            when (mSortType) {
                ScreenType.PRICE_TOP_SORT -> switchSortType(ScreenType.PRICE_BOTTOM_SORT)
                ScreenType.PRICE_BOTTOM_SORT -> switchSortType(ScreenType.PRICE_TOP_SORT)
                else -> switchSortType(ScreenType.PRICE_TOP_SORT)
            }
        }
        ll_screen.setOnClickListener {

        }
    }

    private fun switchSortType(sortType: ScreenType) {
        mSortType = sortType
        initWidgetByType()
    }


    private fun initWidgetByType() {
        ll_normal.getChildAt(0).isSelected = mSortType == ScreenType.NORMAL_SORT || mSortType == ScreenType.COMMENT_SORT
        ll_normal.getChildAt(1).isSelected = mSortType == ScreenType.NORMAL_SORT || mSortType == ScreenType.COMMENT_SORT
        ll_sales.getChildAt(0).isSelected = mSortType == ScreenType.SALE_SORT
        ll_price.getChildAt(0).isSelected = mSortType == ScreenType.PRICE_TOP_SORT || mSortType == ScreenType.PRICE_BOTTOM_SORT
        iv_price_top.setImageResource(if (mSortType == ScreenType.PRICE_TOP_SORT) R.mipmap.arrow_top_primary_dark else R.mipmap.arrow_top_black)
        iv_price_bottom.setImageResource(if (mSortType == ScreenType.PRICE_BOTTOM_SORT) R.mipmap.arrow_bottom_primary_dark else R.mipmap.arrow_bottom_black)
    }


}