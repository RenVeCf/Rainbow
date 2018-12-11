package com.ipd.taxiu.widget

import android.content.Context
import android.graphics.Bitmap
import android.hardware.Camera
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.View
import android.widget.RelativeLayout
import android.widget.VideoView
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom
import com.ipd.taxiu.R
import com.ipd.taxiu.platform.global.GlobalApplication
import com.ipd.taxiu.utils.StringUtils
import com.ipd.taxiu.utils.trimvideo.TrimVideoUtil
import com.ipd.taxiu.widget.camera.CameraInterface
import com.ipd.taxiu.widget.camera.listener.LifecycleListener
import com.ipd.taxiu.widget.camera.listener.RecordResultListener
import kotlinx.android.synthetic.main.activity_shoot_video.view.*

class VideoShootController : RelativeLayout, SurfaceHolder.Callback, CameraInterface.CameraOpenOverCallback, CameraInterface.ErrorCallback, CameraInterface.StopRecordCallback, LifecycleListener {

    object ShootState {
        const val NONE = 0
        const val RECORDING = 1
        const val FINISH = 2
    }

    private var mFlashlightIsOpen = false
    private var mShootState = ShootState.NONE
    private lateinit var mVideoView: VideoView
    private var screenProp = 1f
    private val mTimer: RecordCountDownTimer by lazy { RecordCountDownTimer(TrimVideoUtil.VIDEO_MAX_TIME * 1000L, 100L) }   //录制定时器 }
    private var mVideoDuration = 0L
    private var mRecordResultListener: RecordResultListener? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    private fun init() {

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        CameraInterface.getInstance().setSaveVideoPath(TrimVideoUtil.getTrimmedVideoPath(context))

        resetCameraControllLayout()
        resetFlashlightBtn()

        iv_switch_camera.setOnClickListener {
            CameraInterface.getInstance().switchCamera(mVideoView.holder, screenProp)
        }

        iv_flashlight.setOnClickListener {
            mFlashlightIsOpen = !mFlashlightIsOpen
            resetFlashlightBtn()
        }

        iv_video_cancel.setOnClickListener {
            setShootState(ShootState.NONE)
        }

        iv_video_finish.setOnClickListener {
            mRecordResultListener?.onConfirm()
        }

        iv_camera_controll.setOnClickListener {
            when (mShootState) {
                ShootState.NONE, ShootState.FINISH -> {
                    startRecord()
                }
                ShootState.RECORDING -> {
                    //结束录制
                    stopRecord()
                    //结束计时
                    mTimer.cancel()
                }
            }
        }
    }

    private fun startRecord() {
        //开始录制
        CameraInterface.getInstance().startRecord(mVideoView.holder.surface, screenProp, this)
        setShootState(ShootState.RECORDING)
        //开始计时
        mTimer.start()
    }

    private fun stopRecord() {
        CameraInterface.getInstance().stopRecord(mVideoDuration < 3000L, this)
    }

    private fun resetFlashlightBtn() {
        CameraInterface.getInstance().setFlashMode(if (mFlashlightIsOpen) Camera.Parameters.FLASH_MODE_TORCH else Camera.Parameters.FLASH_MODE_OFF)
        iv_flashlight.setImageResource(if (mFlashlightIsOpen) R.mipmap.flashlight_off else R.mipmap.flashlight_on)
    }

    private fun resetCameraControllLayout() {
        when (mShootState) {
            ShootState.NONE -> {
                iv_camera_controll.setImageResource(R.mipmap.camera_start)
                iv_video_cancel.visibility = View.GONE
                iv_video_finish.visibility = View.GONE
            }
            ShootState.RECORDING -> {
                iv_camera_controll.setImageResource(R.mipmap.camera_pause)
                iv_video_cancel.visibility = View.GONE
                iv_video_finish.visibility = View.GONE
            }
            ShootState.FINISH -> {
                iv_camera_controll.setImageResource(R.mipmap.camera_start)
                iv_video_cancel.visibility = View.VISIBLE
                iv_video_finish.visibility = View.VISIBLE
            }
        }
    }


    fun setVideoView(videoView: VideoView) {
        mVideoView = videoView
        mVideoView.holder.addCallback(this)
    }

    fun setRecordResultListener(listener: RecordResultListener) {
        mRecordResultListener = listener
    }


    override fun onResume() {
        CameraInterface.getInstance().registerSensorManager(context)
        CameraInterface.getInstance().doStartPreview(mVideoView.holder, screenProp)

    }

    override fun onPause() {
        CameraInterface.getInstance().unregisterSensorManager(context)
    }

    override fun onDestory() {
        if (mShootState == ShootState.RECORDING) {
            stopRecord()
            mTimer.cancel()
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        object : Thread() {
            override fun run() {
                CameraInterface.getInstance().doOpenCamera(this@VideoShootController)
            }
        }.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        CameraInterface.getInstance().doDestroyCamera()
    }

    override fun cameraHasOpened() {
        CameraInterface.getInstance().doStartPreview(mVideoView.holder, screenProp)
    }

    /**
     * stopRecord后回调
     */
    override fun recordResult(isShort: Boolean, url: String?, firstFrame: Bitmap?) {
        mRecordResultListener?.onRecodResult(isShort, url, firstFrame)
        if (url == null) {
            setShootState(ShootState.NONE)
        } else {
            setShootState(ShootState.FINISH)
        }
    }

    override fun onError() {
        ToastCommom.getInstance().show(GlobalApplication.mContext,"录制失败...")
        setShootState(ShootState.NONE)
    }

    fun setShootState(state: Int) {
        mShootState = state
        resetCameraControllLayout()
        if (mShootState == ShootState.NONE){
            updateProgress(TrimVideoUtil.VIDEO_MAX_TIME * 1000L)
        }
    }


    //录制视频计时器
    private inner class RecordCountDownTimer internal constructor(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onTick(millisUntilFinished: Long) {
            updateProgress(millisUntilFinished)
        }

        override fun onFinish() {
            updateProgress(0)
            stopRecord()
        }
    }

    private fun updateProgress(millisUntilFinished: Long) {
        mVideoDuration = TrimVideoUtil.VIDEO_MAX_TIME * 1000 - millisUntilFinished
        progress_bar.max = TrimVideoUtil.VIDEO_MAX_TIME * 1000
        progress_bar.progress = mVideoDuration.toInt()
        StringUtils.formatRecordTime(mVideoDuration, { second, millisecond ->
            tv_cur_time.text = "$second.$millisecond"
        })
    }

}