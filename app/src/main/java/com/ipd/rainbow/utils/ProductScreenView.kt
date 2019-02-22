package com.ipd.rainbow.utils

interface ProductScreenView {
    fun getCompositeValue(): Int//综合排序
    fun getSaleValue(): Int//销量排序
    fun getPriceValue(): Int//价格排序
    fun getBrandValue(): String//品牌
    fun getCategoryValue(): String//分类
    fun getCountryValue(): String//国家
}