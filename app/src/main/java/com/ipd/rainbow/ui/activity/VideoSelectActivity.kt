package com.ipd.rainbow.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.VideoAdapter
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.utils.trimvideo.TrimVideoUtil
import kotlinx.android.synthetic.main.activity_video_select.*

class VideoSelectActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, VideoSelectActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "本地视频"
    override fun getContentLayout(): Int = R.layout.activity_video_select

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        TrimVideoUtil.loadAllVideoFiles(mActivity, { errorCode, videos ->
            when (errorCode) {
                0 -> {
                    showContent()
                    recycler_view.adapter = VideoAdapter(mActivity, videos, { pos, info ->
                        if (info.duration > Constant.MAX_VIDEO_TIME) {
                            //视频需要裁剪
                            VideoTrimmerActivity.launch(mActivity, TrimVideoUtil.getVideoFilePath(info.videoPath))
                            return@VideoAdapter
                        }else{
                            VideoCoverActivity.launch(mActivity,TrimVideoUtil.getVideoFilePath(info.videoPath))
                        }
                    })
                }
                1 -> showError("获取视频失败")
                2 -> showError("暂无视频")
            }


        })

    }

    override fun initListener() {
    }

}