package com.ipd.taxiu.utils

object StorePetSpecialType {
    const val SMALL_DOG = 0
    const val BIG_DOG = 1
    const val YOUNG_DOG = 2
    const val YOUNG_CAT = 3
    const val ADULT_CAT = 4
    const val DOG = 10
    const val CAT = 11

    val DOG_TABS: Array<Int> = arrayOf(SMALL_DOG, BIG_DOG, YOUNG_DOG)
    val CAT_TABS: Array<Int> = arrayOf(YOUNG_CAT, ADULT_CAT)
    val DOG_TAB_TITLES: Array<String> = arrayOf("小型犬专区","中大型犬专区","幼犬专区")
    val CAT_TAB_TITLES: Array<String> = arrayOf("幼猫专区","成猫专区")

    fun getParentTypeByType(type: Int): Int {
        return when (type) {
            SMALL_DOG, BIG_DOG, YOUNG_DOG -> DOG
            else -> CAT
        }
    }
}