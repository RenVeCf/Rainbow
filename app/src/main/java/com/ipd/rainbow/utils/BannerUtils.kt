package com.ipd.rainbow.utils

import android.app.Activity
import android.content.Context
import com.ipd.rainbow.bean.BannerBean
import com.ipd.rainbow.ui.activity.store.ProductDetailActivity
import com.ipd.rainbow.ui.activity.store.ProductListActivity
import com.ipd.rainbow.ui.activity.web.WebActivity

object BannerUtils {

    //类型 1超链接 2图文 3商品 4品牌 5H5活动链接
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
            4 -> {
                ProductListActivity.launch(context as Activity,info.CONTENT)
            }
            5 -> {
                WebActivity.launch(context as Activity, WebActivity.URL, info.URL)
            }
        }
    }
}