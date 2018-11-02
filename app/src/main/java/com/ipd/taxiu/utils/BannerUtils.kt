package com.ipd.taxiu.utils

import android.app.Activity
import android.content.Context
import com.ipd.taxiu.bean.BannerBean
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import com.ipd.taxiu.ui.activity.web.WebActivity

object BannerUtils {

    fun setBannerItemClick(context: Context, info: BannerBean) {
        when (info.CATEGORY) {
            1 -> {
                WebActivity.launch(context as Activity, WebActivity.URL, info.URL)
            }
            2 -> {
                WebActivity.launch(context as Activity, WebActivity.HTML, info.CONTENT)
            }
            3 -> {
                ProductDetailActivity.launch(context as Activity, info.PRODUCT_ID, info.FORM_ID)
            }
        }
    }
}