package com.ipd.taxiu.utils.trimvideo

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import com.ipd.ffmpeg.FFmpegApi
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils
import com.ipd.taxiu.bean.VideoInfo
import com.ipd.taxiu.platform.global.GlobalApplication
import com.ipd.taxiu.utils.BackgroundExecutor
import com.ipd.taxiu.utils.SingleCallback
import com.ipd.taxiu.utils.UiThreadExecutor
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import java.util.*


object TrimVideoUtil {

    val MIN_SHOOT_DURATION = 3000L// 最小剪辑时间3s
    val VIDEO_MAX_TIME = 15// 15秒
    val MAX_SHOOT_DURATION = VIDEO_MAX_TIME * 1000L//视频最多剪切多长时间15s
    val MAX_COUNT_RANGE = 15  //seekBar的区域内一共有多少张图片
    private val SCREEN_WIDTH_FULL = GlobalApplication.mContext.resources.displayMetrics.widthPixels
    val RECYCLER_VIEW_PADDING = DensityUtil.dip2px(GlobalApplication.mContext, 35f)
    val VIDEO_FRAMES_WIDTH = SCREEN_WIDTH_FULL - RECYCLER_VIEW_PADDING * 2
    private val THUMB_WIDTH = (SCREEN_WIDTH_FULL - RECYCLER_VIEW_PADDING * 2) / VIDEO_MAX_TIME
    private val THUMB_HEIGHT = DensityUtil.dip2px(GlobalApplication.mContext, 50f)


    fun trim(inputPath: String, outputPath: String, startMs: Long, endMs: Long, callback: TrimVideoListener) {
//        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val outputName = "taXiu_cut.mp4"
        val outputFile = File("$outputPath/$outputName")
        if (outputFile.exists()) {
            outputFile.delete()
        }

        val start = convertSecondsToTime(startMs / 1000)
        val duration = convertSecondsToTime((endMs - startMs) / 1000)

        //裁剪-精确裁剪
        val cmd = "ffmpeg -ss $start -t $duration -accurate_seek -i $inputPath -c copy ${outputFile.absolutePath}"
        val command = cmd.split(" ").toTypedArray()

        callback.onStartTrim()
        FFmpegApi.exec(command) { ret ->
            FFmpegApi.removeListener()
            UiThreadExecutor.runTask("", {
                if (ret == 0) {
                    callback.onFinishTrim(outputFile.absolutePath)
                } else {
                    callback.onErrorTrim()
                }
            }, 0L)

        }

    }


    fun backgroundShootVideoThumb(context: Context, videoUri: Uri, totalThumbsCount: Int, startPosition: Long,
                                  endPosition: Long, callback: SingleCallback<Bitmap, Int>) {
        backgroundShootVideoThumb(context, videoUri, totalThumbsCount, startPosition, endPosition, true, callback)
    }

    fun backgroundShootVideoThumb(context: Context, videoUri: Uri, totalThumbsCount: Int, startPosition: Long,
                                  endPosition: Long, needScale: Boolean, callback: SingleCallback<Bitmap, Int>) {
        LogUtils.e("tag", "totalThumbsCount:$totalThumbsCount,startPosition:$startPosition,endPosition:$endPosition")
        BackgroundExecutor.execute(object : BackgroundExecutor.Task("", 0L, "") {
            override fun execute() {
                try {
//                    val mediaMetadataRetriever = FFmpegMediaMetadataRetriever()
//                    mediaMetadataRetriever.setDataSource(context, videoUri)
//                    // Retrieve media data use microsecond
//                    val interval = (endPosition - startPosition) / (totalThumbsCount - 1)
//                    for (i in 0 until totalThumbsCount) {
//                        val frameTime = startPosition + interval * i
//                        val bitmap = mediaMetadataRetriever.getScaledFrameAtTime(frameTime * 1000, THUMB_WIDTH, THUMB_HEIGHT)
//                        if (bitmap == null) {
//                            LogUtils.e("tag", "pos:$i,bitmap is null...")
//                        } else {
//                            callback.onSingleCallback(bitmap, interval.toInt())
//                        }
//                    }
//                    mediaMetadataRetriever.release()
                    val mediaMetadataRetriever = MediaMetadataRetriever()
                    mediaMetadataRetriever.setDataSource(context, videoUri)
                    // Retrieve media data use microsecond
                    val interval = (endPosition - startPosition) / (totalThumbsCount - 1)
                    for (i in 0 until totalThumbsCount) {
                        val frameTime = startPosition + interval * i
                        var bitmap = mediaMetadataRetriever.getFrameAtTime(frameTime * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)

                        if (needScale) {
                            try {
                                bitmap = Bitmap.createScaledBitmap(bitmap, THUMB_WIDTH, THUMB_HEIGHT, false)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        callback.onSingleCallback(bitmap, interval.toInt())
                    }
                    mediaMetadataRetriever.release()


                } catch (e: Throwable) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e)
                }

            }
        })
    }

    fun loadAllVideoFiles(mContext: Context, callback: (errorCode: Int, videos: List<VideoInfo>?) -> Unit) {
        Observable.create<List<VideoInfo>> {
            val videos = ArrayList<VideoInfo>()
            try {
                val contentResolver = mContext.contentResolver
                val cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null,
                        MediaStore.Video.Media.DATE_MODIFIED + " desc")
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        val videoInfo = VideoInfo()
                        if (cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)) != 0L) {
                            videoInfo.duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))
                            videoInfo.videoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                            videoInfo.createTime = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED))
                            videoInfo.videoName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))
                            videos.add(videoInfo)
                        }
                    }
                    cursor.close()
                }
                it.onNext(videos)
            } catch (t: Throwable) {
                it.onError(t)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<VideoInfo>>() {
                    override fun onNext(t: List<VideoInfo>?) {
                        if (t == null || t.isEmpty()) {
                            callback?.invoke(2, t)
                        } else {
                            callback?.invoke(0, t)
                        }
                    }

                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable?) {
                        callback?.invoke(1, null)
                    }
                })
    }

    fun getVideoWidthHeight(videoPath: String, callback: (errorCode: Int, width: String, height: String) -> Unit) {
        val mmr = MediaMetadataRetriever()
        try {
            if (!TextUtils.isEmpty(videoPath)) {
                mmr.setDataSource(videoPath)
            } else {
                throw NullPointerException("videoPath is null")
            }

            val width = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)//宽
            val height = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)//高
            callback.invoke(0, width, height)
        } catch (ex: Exception) {
            callback.invoke(1, "0", "0")
        } finally {
            mmr.release()
        }

    }

    fun getVideoFilePath(url: String): String {
        var url = url
        if (TextUtils.isEmpty(url) || url.length < 5) return ""
        if (url.substring(0, 4).equals("http", ignoreCase = true)) {

        } else {
            url = "file://$url"
        }

        return url
    }


    fun getTrimmedVideoPath(context: Context): String {
        return context.externalCacheDir.absolutePath
    }


    fun convertSecondsToTime(seconds: Long): String {
        var timeStr: String? = null
        var hour = 0
        var minute = 0
        var second = 0
        if (seconds <= 0) {
            return "00:00"
        } else {
            minute = seconds.toInt() / 60
            if (minute < 60) {
                second = seconds.toInt() % 60
                timeStr = "${unitFormat(minute)}:${unitFormat(second)}"
            } else {
                hour = minute / 60
                if (hour > 99) return "99:59:59"
                minute %= 60
                second = (seconds - (hour * 3600).toLong() - (minute * 60).toLong()).toInt()
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second)
            }
        }
        return timeStr
    }

    private fun unitFormat(i: Int): String {
        var retStr: String? = null
        retStr = if (i >= 0 && i < 10) {
            "0" + Integer.toString(i)
        } else {
            "" + i
        }
        return retStr
    }


}