package com.ipd.taxiu.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.view.ViewTreeObserver
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.utils.trimvideo.TrimVideoUtil
import com.ipd.taxiu.widget.camera.listener.RecordResultListener
import kotlinx.android.synthetic.main.activity_shoot_video.*

class ShootVideoActivity : BaseUIActivity(), RecordResultListener {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, ShootVideoActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "拍摄视频"

    override fun getContentLayout(): Int = R.layout.activity_shoot_video

    override fun initView(bundle: Bundle?) {
        initToolbar()
        camera_layout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                camera_layout.viewTreeObserver.removeGlobalOnLayoutListener(this)
                val params = camera_layout.layoutParams
                params.width = camera_layout.measuredWidth
                params.height = params.width
                camera_layout.layoutParams = params
            }

        })
        video_controller.setVideoView(video_preview)
        video_controller.setRecordResultListener(this)

    }

    override fun loadData() {
    }

    override fun initListener() {
    }

    private var mVideoPath:String? = null
    override fun onRecodResult(isShort: Boolean, path: String?, firstFrame: Bitmap?) {
        if (isShort) {
            toastShow("录制时间不能少于${TrimVideoUtil.MIN_SHOOT_DURATION / 1000}秒")
            return
        }
        mVideoPath = path
    }
    override fun onConfirm() {
        if (TextUtils.isEmpty(mVideoPath)){
            return
        }
        VideoCoverActivity.launch(mActivity,mVideoPath!!)
        finish()
    }

    override fun onResume() {
        super.onResume()
        video_controller.onResume()
    }

    override fun onPause() {
        super.onPause()
        video_controller.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        video_controller.onDestory()
    }


}