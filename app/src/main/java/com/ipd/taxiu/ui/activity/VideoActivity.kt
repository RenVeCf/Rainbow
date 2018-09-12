package com.ipd.taxiu.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import cn.jzvd.Jzvd
import com.ipd.taxiu.R
import com.ipd.taxiu.platform.http.HttpUrl
import com.ipd.taxiu.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_video_watch.*

class VideoActivity : BaseActivity() {
    companion object {
        fun launch(activity: Activity, videoUrl: String) {
            val intent = Intent(activity, VideoActivity::class.java)
            intent.putExtra("videoUrl", videoUrl)
            activity.startActivity(intent)
        }
    }

    private var mVideoUrl = ""
    override fun getBaseLayout(): Int = R.layout.activity_video_watch

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