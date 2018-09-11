package com.ipd.taxiu.ui.activity

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.platform.http.HttpUrl
import com.ipd.taxiu.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_video_watch.*

class VideoActivity : BaseActivity() {
    companion object {
        fun launch(activity: Activity, videoUrl: String) {
            val intent = Intent(activity, VideoActivity::class.java)
            intent.putExtra("videoUrl", HttpUrl.VIDEO_URL + videoUrl)
            activity.startActivity(intent)
        }
    }

    private var mVideoUrl = ""
    private var mPlayingPos = 0
    override fun getBaseLayout(): Int = R.layout.activity_video_watch

    override fun initView(bundle: Bundle?) {
        mVideoUrl = intent.getStringExtra("videoUrl")
        media_controller.setVideoView(video_view)
    }

    override fun loadData() {
        initVideoByURI()

    }

    override fun initListener() {
        video_view.setOnPreparedListener({ mp -> videoPrepared(mp) })
        video_view.setOnCompletionListener({ videoCompleted() })
    }


    fun initVideoByURI() {
        video_view.setVideoURI(Uri.parse(mVideoUrl))
        video_view.requestFocus()
    }

    private fun seekTo(duration: Int) {
        video_view.seekTo(duration)
    }

    private fun videoPrepared(mp: MediaPlayer) {
        mp.start()
        media_controller.onStart()
    }

    private fun videoCompleted() {
        seekTo(0)
        video_view.start()
    }

    override fun onResume() {
        super.onResume()
        if (mPlayingPos > 0) {
            video_view.seekTo(mPlayingPos)
            mPlayingPos = 0
        }
        media_controller.onResume()
    }

    override fun onPause() {
        super.onPause()
        mPlayingPos = video_view.currentPosition
        video_view.stopPlayback()
        media_controller.onPause()
    }
}