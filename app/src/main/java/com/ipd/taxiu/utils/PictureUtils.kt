package com.ipd.taxiu.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.ipd.jumpbox.jumpboxlibrary.utils.ThreadUtils
import com.ipd.taxiu.platform.http.HttpUrl

object PictureUtils {
    fun savePhotoAndRefreshGallery(context: Context, url: String, callback: (code: Int) -> Unit) {
        ThreadUtils.runOnThread {
            try {
                val saveFile = Glide.with(context).load(HttpUrl.IMAGE_URL + url)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get()
                ThreadUtils.runOnUIThread {
                    MediaStore.Images.Media.insertImage(context.contentResolver, saveFile.absolutePath, saveFile.name, null)
                    // 最后通知图库更新
                    val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + saveFile.absolutePath))
                    context.sendBroadcast(intent)

                    callback.invoke(0)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ThreadUtils.runOnUIThread {
                    callback.invoke(1)
                }
            }
        }
    }
}
