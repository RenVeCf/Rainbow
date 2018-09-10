package com.ipd.taxiu.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewTreeObserver
import com.ipd.jumpbox.jumpboxlibrary.bitmap.BitmapUtils
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil
import com.ipd.jumpbox.jumpboxlibrary.utils.LoadingUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.UploadResultBean
import com.ipd.taxiu.event.VideoResultEvent
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.activity.taxiu.PublishTaxiuActivity
import com.ipd.taxiu.utils.UploadUtils
import com.ipd.taxiu.utils.trimvideo.TrimVideoUtil
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

    override fun initView(bundle: Bundle?) {
        initToolbar()

        crop_image_view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                crop_image_view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                val params = crop_image_view.layoutParams
                params.width = crop_image_view.measuredWidth
                params.height = params.width
                crop_image_view.layoutParams = params

                val width = params.width - DensityUtil.dip2px(mActivity, 30f)
                val height = width.toFloat() * 0.56f
                crop_image_view.setCropAreaSize(width, height.toInt())
                crop_image_view.photoView.setImageBitmap(BitmapUtils.getInstance().reSizeBitmap(mActivity, File(coverPath)))

            }

        })

    }

    override fun loadData() {
    }

    override fun initListener() {
        tv_confirm.setOnClickListener {
            val bitmap = crop_image_view.cropImage()
            LoadingUtils.show(this)
            //裁剪后的图片要保存的路径
            val coverPath = "${TrimVideoUtil.getTrimmedVideoPath(mActivity)}/taXiu_cover.png"
            val coverFile = File(coverPath)
            //删除已经存在的图片
            if (coverFile.exists()) {
                coverFile.delete()
            }
            com.ipd.jumpbox.jumpboxlibrary.utils.BitmapUtils.savePhotoToSDCard(bitmap, coverPath)

            if (coverFile.exists()) {
                //上传图片
                UploadUtils.uploadPic(mActivity, true, coverPath, object : UploadUtils.UploadCallback {
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
    }

}