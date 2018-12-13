package com.ipd.taxiu.ui.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import cn.jzvd.Jzvd
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_video_watch.*

class VideoActivity : BaseActivity() {
    companion object {
        fun launch(activity: Activity, videoUrl: String, width: Int = 0, height: Int = 0) {
            val intent = Intent(activity, VideoActivity::class.java)
            intent.putExtra("videoUrl", videoUrl)
            intent.putExtra("width", width)
            intent.putExtra("height", height)
            activity.startActivity(intent)
        }
    }

    private var mVideoUrl = ""
    override fun getBaseLayout(): Int = R.layout.activity_video_watch

    override fun initView(bundle: Bundle?) {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)

        mVideoUrl = intent.getStringExtra("videoUrl")

        videoplayer.setUp(mVideoUrl,
                "",
                Jzvd.SCREEN_WINDOW_FULLSCREEN)
        videoplayer.startButton.performClick()

    }

    override fun loadData() {

    }

    override fun initListener() {
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (Jzvd.backPress()) {
            return
        }
    }


    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }
}