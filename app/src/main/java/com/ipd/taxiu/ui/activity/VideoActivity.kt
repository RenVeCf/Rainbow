package com.ipd.taxiu.ui.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
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

    override fun setActivityScreenOrientation() {
//        val width = intent.getIntExtra("width", 0)
//        val height = intent.getIntExtra("height", 0)
//        if (width == 0 || height == 0) {
//            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//            return
//        }
//        requestedOrientation = if (width > height) {
//            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//        } else {
//            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//        }
    }

    override fun initView(bundle: Bundle?) {
        mVideoUrl = intent.getStringExtra("videoUrl")

        videoplayer.setUp(mVideoUrl,
                "",
                Jzvd.SCREEN_WINDOW_FULLSCREEN)

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