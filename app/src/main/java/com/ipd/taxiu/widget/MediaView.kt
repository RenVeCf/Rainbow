package com.ipd.taxiu.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ipd.taxiu.R
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.ui.activity.PictureLookActivity
import kotlinx.android.synthetic.main.item_topic_comment_img.view.*

class MediaView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val IMAGE = 0
    val VIDEO = 1
    val MAX_SIZE = 3
    private var mType = IMAGE
    private val mInflater by lazy { LayoutInflater.from(context) }
    private var viewHeight: Int = resources.getDimension(R.dimen.topic_comment_img_height).toInt()

    private var list: List<String>? = null
    fun setPics(list: List<String>) {
        mType = IMAGE
        viewHeight = resources.getDimension(R.dimen.topic_comment_img_height).toInt()
        this.list = list

        addMediaView()
    }


    fun setVideo(logo: String) {
        mType = VIDEO
        viewHeight = resources.getDimension(R.dimen.taxiu_video_img_height).toInt()
        list = arrayListOf(logo)

        addMediaView()
    }


    private fun addMediaView() {
        val childWidth = (parent as ViewGroup).measuredWidth.toFloat() / (if (list!!.size >= MAX_SIZE) MAX_SIZE else list!!.size)
        list?.forEachIndexed { index, s ->
            if (index >= MAX_SIZE) return@forEachIndexed
            val mediaView = mInflater.inflate(R.layout.item_topic_comment_img, this, false)
            mediaView.layoutParams.width = childWidth.toInt()

            ImageLoader.loadNoPlaceHolderImg(context, s, mediaView.iv_topic_comment_image)
            mediaView.setOnClickListener {
                PictureLookActivity.launch(context as Activity?, ArrayList(list), index, PictureLookActivity.URL)
            }
            addView(mediaView)
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY))
    }

}