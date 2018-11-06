package com.ipd.taxiu.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ClassRoomBean
import com.ipd.taxiu.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_classroom.view.*

class ClassRoomLayout : FrameLayout {
    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    private val mContentView: View by lazy { LayoutInflater.from(context).inflate(R.layout.item_classroom, this, false) }

    private fun init() {
        addView(mContentView)
    }

    fun setData(isBuyed: Boolean, info: ClassRoomBean) {
        if (isBuyed) {
            setBtnByStatus(info.CLASS_STATE)
        } else {
            if (info.CLASS_STATE == 3) {
                //已结束
                setBtnByStatus(info.CLASS_STATE)
            } else {
                mContentView.tv_classroom_buy.setBackgroundResource(R.drawable.shape_buy_bg)
                mContentView.tv_classroom_buy.setTextColor(resources.getColor(R.color.white))
                mContentView.tv_classroom_buy.text = "￥${info.PRICE}  购买"
            }

        }

        ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, mContentView.iv_classroom_image)
        mContentView.tv_classroom_name.text = info.TITLE
        mContentView.tv_classroom_desc.text = "开课时间：${info.BEGIN_TIME}"


    }

    private fun setBtnByStatus(status: Int) {
        when (status) {
            1 -> {
                mContentView.tv_classroom_buy.text = "未开始"
                mContentView.tv_classroom_buy.setTextColor(Color.parseColor("#51A151"))
                mContentView.tv_classroom_buy.setBackgroundResource(R.drawable.shape_classroom_wait_start)
            }
            2 -> {
                mContentView.tv_classroom_buy.text = "进行中"
                mContentView.tv_classroom_buy.setTextColor(Color.parseColor("#EB6717"))
                mContentView.tv_classroom_buy.setBackgroundResource(R.drawable.shape_classroom_underway)
            }
            3 -> {
                mContentView.tv_classroom_buy.text = "已结束"
                mContentView.tv_classroom_buy.setTextColor(Color.parseColor("#B8B8B8"))
                mContentView.tv_classroom_buy.setBackgroundResource(R.drawable.shape_classroom_end)
            }
        }
    }
}