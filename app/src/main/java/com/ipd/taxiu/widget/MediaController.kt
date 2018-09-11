package com.ipd.taxiu.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.VideoView
import com.ipd.taxiu.R
import kotlinx.android.synthetic.main.layout_media_controller.view.*

class MediaController : FrameLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val mContentView by lazy { LayoutInflater.from(context).inflate(R.layout.layout_media_controller, this, false) }

    override fun onFinishInflate() {
        super.onFinishInflate()
        addView(mContentView)

        setOnTouchListener { v, event ->
            if (mVideoView != null) {
                if (mVideoView!!.isPlaying) {
                    mVideoView!!.pause()
                } else {
                    mVideoView!!.start()
                }
                resetPlayBtn()
            }

            return@setOnTouchListener false
        }
    }

    private fun resetPlayBtn() {
        iv_play.visibility = if (mVideoView!!.isPlaying) View.GONE else View.VISIBLE
    }


    private var mVideoView: VideoView? = null
    fun setVideoView(videoView: VideoView) {
        mVideoView = videoView
    }

    fun onStart() {
        resetPlayBtn()
    }

    fun onResume() {
        post(mShowProgress)
    }

    fun onPause() {
        removeCallbacks(mShowProgress)
    }


    private val mShowProgress = object : Runnable {
        override fun run() {
            val pos = mVideoView?.currentPosition ?: 0
            val max = mVideoView?.duration ?: 100
            progress_bar.max = max
            progress_bar.progress = pos
            postDelayed(this, (1000 - pos % 1000).toLong())
        }
    }


}