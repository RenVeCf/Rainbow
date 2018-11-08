package com.ipd.taxiu.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.jumpbox.jumpboxlibrary.utils.LoadingUtils
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.platform.global.GlobalApplication
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.utils.PictureChooseUtils
import com.steelkiwi.cropiwa.AspectRatio
import com.steelkiwi.cropiwa.config.CropIwaSaveConfig
import kotlinx.android.synthetic.main.activity_crop.*
import java.io.File

/**
 * Created by jumpbox on 2017/3/3.
 */

class CropActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, path: String) {
            val intent = Intent(activity, CropActivity::class.java)
            intent.putExtra("path", path)
            activity.startActivityForResult(intent, PictureChooseUtils.IMAGE_COMPLETE)
        }
    }

    private val mPath: String by lazy { intent.getStringExtra("path") }

    public override fun getToolbarTitle(): String {
        return "裁剪"
    }

    public override fun getContentLayout(): Int {
        return R.layout.activity_crop
    }

    public override fun initView(savedInstanceState: Bundle?) {
        initToolbar()
        crop_image_view
                .configureOverlay()
                .setDynamicCrop(false)
                .setAspectRatio(AspectRatio(1, 1))
                .apply()

        crop_image_view.setImageUri(Uri.fromFile(File(mPath)))
    }

    public override fun loadData() {

    }

    public override fun initListener() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.save_photo_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_save) {
            //保存
            val savePath = CommonUtils.getPhotoSavePath(GlobalApplication.mContext) + "/" + "avatar.png"
            val saveFile = File(savePath)
            //删除已经存在的图片
            if (saveFile.exists()) {
                saveFile.delete()
            }

            crop_image_view.crop(CropIwaSaveConfig.Builder(Uri.fromFile(saveFile))
                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                    .setQuality(100)
                    .build())

            crop_image_view.setCropSaveCompleteListener {
                LogUtils.e("tag", it.toString())
                uploadPic(savePath)
            }

            crop_image_view.setErrorListener {
                toastShow("裁剪失败")
            }

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun uploadPic(path: String) {
        result("", "", path)
    }


    fun result(url: String, name: String, path: String) {
        try {
            val intent = Intent()
            intent.putExtra("url", url)
            intent.putExtra("name", name)
            intent.putExtra("path", path)
            setResult(Activity.RESULT_OK, intent)
            finish()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            LoadingUtils.dismiss()
        }
    }

    override fun onViewAttach() {

    }

    override fun onViewDetach() {

    }
}
