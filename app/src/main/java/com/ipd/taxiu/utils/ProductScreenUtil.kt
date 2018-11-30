package com.ipd.taxiu.utils

import com.ipd.taxiu.bean.ProductExpertScreenBean
import com.ipd.taxiu.bean.ProductExpertScreenResultBean

object ProductScreenUtil {

    /**
     * 将商品筛选条件添加到集合中
     * 根据该集合动态添加筛选item
     */
    fun getStoreScreenProductList(resuleData: ProductExpertScreenResultBean): List<ProductExpertScreenBean> {
        val screenList = ArrayList<ProductExpertScreenBean>()
        if (resuleData.BRAND_DATA != null) {
            screenList.add(resuleData.BRAND_DATA)
        }
        if (resuleData.APPLY_DATA != null) {
            screenList.add(resuleData.APPLY_DATA)
        }
        if (resuleData.SIZE_DATA != null) {
            screenList.add(resuleData.SIZE_DATA)
        }
//        if (resuleData.TYPE_DATA != null) {
//            screenList.add(resuleData.TYPE_DATA)
//        }
        if (resuleData.NET_DATA != null) {
            screenList.add(resuleData.NET_DATA)
        }
        if (resuleData.TASTE_DATA != null) {
            screenList.add(resuleData.TASTE_DATA)
        }
        if (resuleData.COUNTRY_DATA != null) {
            screenList.add(resuleData.COUNTRY_DATA)
        }
        if (resuleData.THING_DATA != null) {
            screenList.add(resuleData.THING_DATA)
        }
        return screenList
    }


    fun getScreenInfoByTitle(title: String) {
        when (title) {

        }
    }
}