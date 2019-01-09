package com.ipd.rainbow.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.ViewTreeObserver
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.UploadResultBean
import com.ipd.rainbow.event.VideoResultEvent
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.activity.taxiu.PublishTaxiuActivity
import com.ipd.rainbow.utils.UploadUtils
import com.ipd.rainbow.utils.trimvideo.TrimVideoUtil
import com.steelkiwi.cropiwa.AspectRatio
import com.steelkiwi.cropiwa.config.CropIwaSaveConfig
import kotlinx.android.synthetic.main.activity_video_cover_crop.*
import org.greenrobot.eventbus.EventBus
import java.io.File


class VideoCoverCropActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, videoPath: String, coverPath: String) {
            val intent = Intent(activity, VideoCoverCropActivity::class.java)
            intent.putExtra("videoPath", videoPath)
            intent.putExtra("coverPath", coverPath)
            activity.startActivity(intent)
        }
    }

    private val videoPath: String by lazy { intent.getStringExtra("videoPath") }
    private val coverPath: String by lazy { intent.getStringExtra("coverPath") }

    override fun getToolbarTitle(): String = "裁剪封面"


    override fun getContentLayout(): Int = R.layout.activity_video_cover_crop

    lateinit var savePath: String
    override fun initView(bundle: Bundle?) {
        initToolbar()
        savePath = "${TrimVideoUtil.getTrimmedVideoPath(mActivity)}/taXiu_cover2.png"

        crop_image_view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                crop_image_view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                val params = crop_image_view.layoutParams
                params.width = crop_image_view.measuredWidth
                params.height = params.width
                crop_image_view.layoutParams = params

                val width = params.width - DensityUtil.dip2px(mActivity, 30f)
                val height = width.toFloat() * 0.56f

                crop_image_view.configureOverlay()
                        .setDynamicCrop(false)
                        .setAspectRatio(AspectRatio(DensityUtil.px2dip(mActivity, width.toFloat()), DensityUtil.px2dip(mActivity, height)))
                        .apply()
                crop_image_view.setImageUri(Uri.fromFile(File(coverPath)))
            }
        })

    }

    override fun loadData() {
    }

    override fun initListener() {
        tv_confirm.setOnClickListener {
            tv_confirm.isEnabled = false

            val saveFile = File(savePath)
            //删除已经存在的图片
            if (saveFile.exists()) {
                saveFile.delete()
            }

            crop_image_view.crop(CropIwaSaveConfig.Builder(Uri.fromFile(saveFile))
                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                    .setQuality(100)
                    .build())
        }


        crop_image_view.setCropSaveCompleteListener {
            LogUtils.e("tag", it.toString())
            tv_confirm.isEnabled = true

            val saveFile = File(savePath)
            if (saveFile.exists()) {
                //上传图片
                UploadUtils.uploadPic(mActivity, true, savePath, object : UploadUtils.UploadCallback {
                    override fun onProgress(progress: Int) {

                    }

                    override fun uploadSuccess(resultBean: UploadResultBean) {
                        EventBus.getDefault().post(VideoResultEvent(resultBean.data, videoPath))
                        PublishTaxiuActivity.launch(mActivity, PublishTaxiuActivity.VIDEO)
                        finish()
                    }

                    override fun uploadFail(errMsg: String) {
                        toastShow(errMsg)
                    }

                })


            } else {
                toastShow("裁剪失败...")
            }
        }

        crop_image_view.setErrorListener {
            tv_confirm.isEnabled = true

            toastShow("裁剪失败")
        }
    }

}