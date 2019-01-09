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
    const val PRODUCT_NORMAL = 1//普通商品
    const val PRODUCT_FLASH_SALE = 2//限时抢购
    const val PRODUCT_CLEARANCE = 3//清仓
    const val PRODUCT_NEW = 4//新品上新
    const val PRODUCT_GROUP_PRODUCT = 5//组合商品
    const val PRODUCT_GROUP_PURCHASE = 6//拼团


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