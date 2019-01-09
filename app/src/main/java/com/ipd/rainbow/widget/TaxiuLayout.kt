package com.ipd.rainbow.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.MediaPictureAdapter
import com.ipd.rainbow.adapter.MediaVideoAdapter
import com.ipd.rainbow.bean.TaxiuBean
import com.ipd.rainbow.bean.VideoShowBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.utils.StringUtils
import kotlinx.android.synthetic.main.item_taxiu.view.*

class TaxiuLayout : FrameLayout {
    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    private val mContentView by lazy { LayoutInflater.from(context).inflate(R.layout.item_taxiu, this, false) }

    private fun init() {
        addView(mContentView)
    }

    fun setData(info: TaxiuBean) {
        mContentView.tv_taxiu_name.text = info.CONTENT
//        mContentView.tv_taxiu_lable.text = info.TIP
        mContentView.taxiu_lable_show_layout.setLableInfo(info.ShowTipList)


        if (TextUtils.isEmpty(info.URL)) {
            //pic
            val pics = StringUtils.splitImages(info.PIC)
            if (pics.isNotEmpty()) {
                mContentView.media_recycler_view.visibility = View.VISIBLE
                mContentView.media_recycler_view.adapter = MediaPictureAdapter(context, pics, null)
            } else {
                mContentView.media_recycler_view.visibility = View.GONE
            }
        } else {
            //video
            mContentView.media_recycler_view.visibility = View.VISIBLE
            mContentView.media_recycler_view.adapter = MediaVideoAdapter(context, arrayListOf(VideoShowBean(info.LOGO, info.URL)), null)
        }


        ImageLoader.loadAvatar(context, info.USER_LOGO, mContentView.user_avatar)
        mContentView.tv_username.text = info.USER_NICKNAME
        mContentView.tv_viewers_num.text = info.BROWSE.toString()
        mContentView.tv_comment_num.text = info.REPLY.toString()
        mContentView.tv_collect_num.text = info.COLLECT.toString()


    }

}