package com.ipd.rainbow.utils

object StoreType {
    const val GUESS_LIKE_INDEX = 0
    const val GUESS_LIKE_SMALL_DOG = 1
    const val GUESS_LIKE_BIG_DOG = 2
    const val GUESS_LIKE_YOUNG_DOG = 3
    const val GUESS_LIKE_YOUNG_CAT = 4
    const val GUESS_LIKE_ADULT_CAT = 5


    //品牌
    const val PRODUCT_BRAND_ALL = 0
    const val PRODUCT_BRAND_RECOMMEND = 0


    //限时抢购
    const val FLASH_SALE_TODAY = 1
    const val FLASH_SALE_TOMORROW = 2

    //商品类型
    const val PRODUCT_NORMAL = 0//普通商品
    const val PRODUCT_TODAY_SALES = 1//限时抢购
    const val PRODUCT_TODAY_NEW = 2//新品上新
    const val PRODUCT_STOCK = 3//优选库存
    const val PRODUCT_GROUP_PURCHASE = 4//拼团
    const val PRODUCT_VIP = 5//会员专享


    fun getGuessLikeTypeBySpecial(specialType: Int): Int {
        return when (specialType) {
            StorePetSpecialType.SMALL_DOG -> GUESS_LIKE_SMALL_DOG
            StorePetSpecialType.BIG_DOG -> GUESS_LIKE_BIG_DOG
            StorePetSpecialType.YOUNG_DOG -> GUESS_LIKE_YOUNG_DOG
            StorePetSpecialType.YOUNG_CAT -> GUESS_LIKE_YOUNG_CAT
            StorePetSpecialType.ADULT_CAT -> GUESS_LIKE_ADULT_CAT
            else -> GUESS_LIKE_INDEX
        }
    }

}