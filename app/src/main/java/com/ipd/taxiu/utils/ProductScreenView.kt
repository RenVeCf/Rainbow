package com.ipd.taxiu.utils

interface ProductScreenView {
    fun getCompositeValue(): Int//综合排序
    fun getSaleValue(): Int//销量排序
    fun getPriceValue(): Int//价格排序
    fun getMinPrice(): Float//最小价格
    fun getMaxPrice(): Float//最大价格
    fun getApplyValue(): String//适用阶段
    fun getSizeValue(): String//宠物体型
    fun getPetTypeValue(): String//宠物品种
    fun getNetContentValue(): String//净含量
    fun getTasteValue(): String//口味
    fun getCountryValue(): String//国家
    fun getThingTypeValue(): String//用品分类
}