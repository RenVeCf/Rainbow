package com.ipd.taxiu.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewTreeObserver
import com.ipd.jumpbox.jumpboxlibrary.utils.BitmapUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.VideoCoverAdapter
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.utils.SingleCallback
import com.ipd.taxiu.utils.UiThreadExecutor
import com.ipd.taxiu.utils.trimvideo.TrimVideoUtil
import kotlinx.android.synthetic.main.activity_video_cover.*
import rx.Observable

class VideoCoverActivity : BaseUIActivity(), VideoCoverAdapter.VideoCoverListener {

    companion object {
        fun launch(activity: Activity, videoPath: String) {
            val intent = Intent(activity, VideoCoverActivity::class.java)
            intent.putExtra("videoPath", videoPath)
            activity.startActivity(intent)
        }
    }


    private val videoPath: String by lazy { intent.getStringExtra("videoPath") }
    private val mCoverAdapter: VideoCoverAdapter by lazy { VideoCoverAdapter(mActivity, arrayListOf(), this) }


    override fun getToolbarTitle(): String = "选择封面"

    override fun getContentLayout(): Int = R.layout.activity_video_cover

    override fun initView(bundle: Bundle?) {
        initToolbar()

        layout_surface_view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                layout_surface_view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                val params = layout_surface_view.layoutParams
                params.width = layout_surface_view.measuredWidth
                params.height = params.width
                layout_surface_view.layoutParams = params
            }

        })
    }


    override fun loadData() {
        video_frames_recyclerView.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false)
        video_frames_recyclerView.adapter = mCoverAdapter

        val mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(videoPath)
        mediaPlayer.prepare()
        val duration = mediaPlayer.duration

        TrimVideoUtil.backgroundShootVideoThumb(mActivity, Uri.parse(videoPath), duration / 1000, 0, duration.toLong(), false,
                SingleCallback { bitmap, pos ->
                    UiThreadExecutor.runTask("", { mCoverAdapter.addBitmap(bitmap) }, 0L)
                })
    }

    override fun initListener() {
        tv_confirm.setOnClickListener {
            val bitmap = mCoverAdapter?.getSelectedBitmap()
            if (bitmap == null) {
                toastShow("请选择封面")
                return@setOnClickListener
            } else {
                //保存封面bitmap
                Observable.create<String> {
                    val coverPath = "${TrimVideoUtil.getTrimmedVideoPath(mActivity)}/taXiu_cover.png"
                    BitmapUtils.savePhotoToSDCard(bitmap, coverPath)
                    it.onNext(coverPath)
                    it.onCompleted()
                }.compose(RxScheduler.applyScheduler())
                        .subscribe(object : Response<String>(mActivity, true) {
                            override fun _onNext(coverPath: String) {
                                VideoCoverCropActivity.launch(mActivity, videoPath, coverPath)
                                finish()
                            }

                        })

            }
        }
    }

    override fun onFirstInited(bitmap: Bitmap) {
        frame_image.setImageBitmap(bitmap)
    }

    override fun onSelectChanged(bitmap: Bitmap) {
        frame_image.setImageBitmap(bitmap)
    }

}