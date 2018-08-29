package com.ipd.taxiu.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.TopicBean
import com.ipd.taxiu.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_topic.view.*

class TopicLayout : FrameLayout {
    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    private val mContentView by lazy { LayoutInflater.from(context).inflate(R.layout.item_topic, this, false) }

    private fun init() {
        addView(mContentView)
    }

    fun setData(info: TopicBean) {
        mContentView.tv_title.text = info.TITLE
        mContentView.tv_desc.text = info.CONTENT

        if (!TextUtils.isEmpty(info.LOGO)) {
            mContentView.iv_classroom_image.visibility = View.VISIBLE
            ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, mContentView.iv_classroom_image)
        } else {
            mContentView.iv_classroom_image.visibility = View.GONE
        }



        mContentView.tv_viewers_num.text = info.BROWSE.toString()
        mContentView.tv_comment_num.text = info.REPLY.toString()
        mContentView.tv_collect_num.text = info.COLLECT.toString()
        mContentView.tv_zan_num.text = info.PRAISE.toString()


    }

}