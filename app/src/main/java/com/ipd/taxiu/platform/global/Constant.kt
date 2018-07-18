package com.ipd.taxiu.platform.global

/**
 * Created by jumpbox on 2018/6/25.
 */
object Constant {
    const val FAVORITE_ID: Int = 1001
    const val DEVICE_UUID: String = "00001101-0000-1000-8000-00805f9b34fb"
    const val BRAIN_WAVE_INTERVAL_TIME: Long = 100L //脑电波更新间隔时间
    const val STATISTICS_SCORE_LOOP_TIME: Long = 1000 * 60 //统计页面分数更新时间
    const val STATISTICS_BIGPACKAGE_LOOP_TIME: Long = 1000 * 60 //统计页面图框更新时间
    const val SONG_SHEET_UPDATE_INTEGRAL_TIME: Long = 1000 * 60 * 60 * 24 //歌单列表更新间隔时间
    const val BIG_PACKAGE_UPLOAD_LOOP_TIME: Long = 1000 * 60 //大包上传间隔时间
    val SHARE_PIC_PATH: String = "${GlobalApplication.mContext.getExternalFilesDir(null).absolutePath}/share.png"
}