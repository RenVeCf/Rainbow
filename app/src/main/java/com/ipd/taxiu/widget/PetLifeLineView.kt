package com.ipd.taxiu.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.Scroller
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.LifeLineBean
import kotlinx.android.synthetic.main.item_pet_life_line.view.*

class PetLifeLineView : LinearLayout {
    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    private val SHOW_NUM = 3 //最多显示3个
    private val mInflater: LayoutInflater by lazy { LayoutInflater.from(context) }
    private var lifeLineList: List<LifeLineBean>? = null
    private var mMeasureWidth = 0
    private val mScroller: Scroller = Scroller(context)

    private fun init() {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mMeasureWidth = measuredWidth
    }

    fun setLifeLineData(lifeLineList: List<LifeLineBean>) {
        if (lifeLineList.isEmpty()) return
        this.lifeLineList = lifeLineList

        removeAllViews()
        lifeLineList.forEachIndexed { index, info ->
            val lifeLineView = mInflater.inflate(R.layout.item_pet_life_line, this, false)
            var lifeStr = "${info.MONTH_NUM}个月${info.DAY_NUM}天"
            lifeLineView.tv_pet_life_line.text = lifeStr
            lifeLineView.tv_pet_life_line_date.text = info.date
            val params = lifeLineView.layoutParams
            params.width = getItemWidth()
            addView(lifeLineView)
        }
        if (lifeLineList.size == 1) {
            scrollTo(-getItemWidth(), 0)
            mOnPositionChangeListener?.onChange(0)
        } else {
            mOnPositionChangeListener?.onChange(1)
        }

    }

    private fun getItemWidth(): Int {
        return mMeasureWidth / SHOW_NUM
    }

    private var isBeginDragged = false
    private var pressX: Float = 0f
    private var pressY: Float = 0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (childCount == 0) return super.onTouchEvent(event)
                pressX = event.x
                pressY = event.y
                parent.requestDisallowInterceptTouchEvent(true)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - pressX
                val dy = event.y - pressY

                pressX = event.x
                pressY = event.y
                if (isBeginDragged || Math.abs(dx) > Math.abs(dy).minus(1)) {
                    isBeginDragged = true
                    scrollBy(0 - dx.toInt(), 0)
                    return true
                }
                parent.requestDisallowInterceptTouchEvent(false)

            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                isBeginDragged = false
                val distance = scrollX % getItemWidth()
//                LogUtils.e("tag", "scrollX:$scrollX,distance:$distance,itemWidth:${getItemWidth()},width:$width")

                val maxScrollX = getChildAt(childCount - 1).left - getItemWidth()
                if (scrollX < -getItemWidth() / 2) {
                    onPositionChange(-getItemWidth())
                    mScroller.startScroll(scrollX, scrollY, -getItemWidth() - scrollX, 0)
                } else if (scrollX > maxScrollX) {
                    onPositionChange(maxScrollX)
                    mScroller.startScroll(scrollX, scrollY, maxScrollX - scrollX, 0)
                } else if (distance > getItemWidth() * 0.5) {
                    onPositionChange(scrollX + getItemWidth() - distance)
                    mScroller.startScroll(scrollX, scrollY, getItemWidth() - distance, 0)
                } else {
                    onPositionChange(scrollX - distance)
                    mScroller.startScroll(scrollX, scrollY, -distance, 0)
                }
                postInvalidate()

            }
        }
        return super.onTouchEvent(event)
    }

    private var mPosition = 1
    private fun onPositionChange(scrollX: Int) {
        val position = getPostionByScrollX(scrollX)
        if (mPosition != position) {
            mPosition = position
            mOnPositionChangeListener?.onChange(position)
        }
    }

    private fun getPostionByScrollX(scrollX: Int): Int {
        return scrollX / getItemWidth() + 1
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            invalidate()
        }
    }


    private var mOnPositionChangeListener: OnPositionChangeListener? = null
    fun setPositionChangeListener(listener: OnPositionChangeListener) {
        mOnPositionChangeListener = listener
    }

    interface OnPositionChangeListener {
        fun onChange(pos: Int)
    }
}