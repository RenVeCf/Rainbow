package com.ipd.rainbow.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import com.ipd.jumpbox.jumpboxlibrary.utils.LoadingUtils
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.utils.trimvideo.TrimVideoListener
import kotlinx.android.synthetic.main.activity_video_trimmer.*

class VideoTrimmerActivity : BaseUIActivity(), TrimVideoListener {

    companion object {
        fun launch(activity: Activity, videoPath: String) {
            val intent = Intent(activity, VideoTrimmerActivity::class.java)
            intent.putExtra("videoPath", videoPath)
            activity.startActivity(intent)
        }
    }

    private val videoPath: String by lazy { intent.getStringExtra("videoPath") }
    private var mBackPressed: Boolean = false

    override fun getToolbarTitle(): String = "视频裁剪"

    override fun getContentLayout(): Int = R.layout.activity_video_trimmer

    override fun initView(bundle: Bundle?) {
        initToolbar()

        trimmer_view.setOnTrimVideoListener(this)
        trimmer_view.initVideoByURI(Uri.parse(videoPath))
    }

    override fun loadData() {
    }

    override fun initListener() {
    }


    override fun onPause() {
        super.onPause()
        trimmer_view.onVideoPause()
        trimmer_view.setRestoreState(true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mBackPressed = true
    }

    override fun onStop() {
        super.onStop()
        trimmer_view.onStop(mBackPressed)
    }

    override fun onDestroy() {
        super.onDestroy()
        trimmer_view.onDestroy()
    }

    override fun onStartTrim() {
        LogUtils.e("tag", "onStartTrim")
        LoadingUtils.show(mActivity, "裁剪中...")
        LoadingUtils.setOnKeyListener { dialog, keyCode, event ->
            if (event.keyCode == KeyEvent.KEYCODE_BACK) {
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    override fun onFinishTrim(url: String) {
        LoadingUtils.dismiss()
        if (!TextUtils.isEmpty(url)) {
            VideoCoverActivity.launch(mActivity, url)
            finish()
        } else {
            toastShow("视频不存在")
        }
    }

    override fun onErrorTrim() {
        LoadingUtils.dismiss()
        toastShow("裁剪失败")
    }

    override fun onCancel() {
        finish()
    }


}