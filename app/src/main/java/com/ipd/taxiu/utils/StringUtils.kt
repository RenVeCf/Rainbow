package com.ipd.taxiu.utils

object StringUtils {
    val ss = 1000
    val mi = ss * 60
    val hh = mi * 60
    val dd = hh * 24
    fun getCountDownByTime(ms: Long, callback: (hours: String, minutes: String, second: String) -> Unit) {
        val hour = ms / hh
        val minutes = ms % hh / mi
        val second = ms % hh % mi / ss
        callback.invoke(formatTimeStr(hour), formatTimeStr(minutes), formatTimeStr(second))
    }

    private fun formatTimeStr(time: Long): String {
        return if (time < 10) "0$time" else time.toString()
    }


}
