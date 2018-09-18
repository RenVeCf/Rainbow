package com.ipd.taxiu.utils

object StoreType {
    const val GUESS_LIKE_INDEX = 0
    const val GUESS_LIKE_SMALL_DOG = 1
    const val GUESS_LIKE_BIG_DOG = 2
    const val GUESS_LIKE_YOUNG_DOG = 3
    const val GUESS_LIKE_YOUNG_CAT = 4
    const val GUESS_LIKE_ADULT_CAT = 5



    const val PRODUCT_BRAND_ALL = 0
    const val PRODUCT_BRAND_RECOMMEND = 0



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