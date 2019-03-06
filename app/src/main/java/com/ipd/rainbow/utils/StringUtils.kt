package com.ipd.rainbow.utils

import android.text.TextUtils
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.widget.ChoosePayTypeLayout

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

    fun formatRecordTime(ms: Long, callback: (second: Long, millisecond: Long) -> Unit) {
        val second = ms % hh % mi / ss
        val millisecond = (ms % hh % mi % ss) / 100
        callback.invoke(second, millisecond)
    }

    fun formatTimeStr(time: Long): String {
        return if (time < 10) "0$time" else time.toString()
    }

    fun formatTimeStr(time: Int): String {
        return if (time < 10) "0$time" else time.toString()
    }


    fun splitImages(picStr: String): List<String> {
        if (TextUtils.isEmpty(picStr)) {
            return arrayListOf()
        }
        var fixPicStr = picStr
        if (fixPicStr.last() == ';') {
            fixPicStr = fixPicStr.substring(0, picStr.length - 1)
        }

        if (fixPicStr.contains(";")) {
            return fixPicStr.split(";")
        }
        return arrayListOf(fixPicStr)
    }

    fun fixedPicStr(picStr: String): String {
        if (TextUtils.isEmpty(picStr)) {
            return picStr
        }
        var fixPicStr = picStr
        if (fixPicStr.last() == ';') {
            fixPicStr = fixPicStr.substring(0, fixPicStr.length - 1)
        }
        return fixPicStr
    }

    fun getPayStrByPayType(payType: Int): String {
        return when (payType) {
            ChoosePayTypeLayout.PayType.ALIPAY -> "支付宝"
            ChoosePayTypeLayout.PayType.WECHAT -> "微信"
            ChoosePayTypeLayout.PayType.BALANCE -> "余额"
            else -> ""
        }
    }


    fun priceNeedEncry(price: String, kind: Int): Boolean {
        if (GlobalParam.isLogin()) {
            return false
        }
        return when (kind) {
            StoreType.FLASH_SALE_TODAY, StoreType.PRODUCT_TODAY_NEW -> {
                true
            }
            else -> false
        }
    }

    fun getEncryPrice(needSymbol: Boolean, price: String, kind: Int): String {
        return if (priceNeedEncry(price, kind)) {
            LogUtils.e("tag", "****")
            "****"
        } else {
            LogUtils.e("tag","￥$price")
            if (needSymbol) "￥$price" else price
        }
    }

}
