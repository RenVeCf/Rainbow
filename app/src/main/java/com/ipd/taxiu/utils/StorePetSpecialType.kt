package com.ipd.taxiu.utils

object StorePetSpecialType {
    const val SMALL_DOG = 1
    const val BIG_DOG = 2
    const val YOUNG_DOG = 3
    const val YOUNG_CAT = 4
    const val ADULT_CAT = 5
    const val DOG = 1
    const val CAT = 2

    val DOG_TABS: Array<Int> = arrayOf(SMALL_DOG, BIG_DOG, YOUNG_DOG)
    val CAT_TABS: Array<Int> = arrayOf(YOUNG_CAT, ADULT_CAT)
    val DOG_TAB_TITLES: Array<String> = arrayOf("小型犬专区", "中大型犬专区", "幼犬专区")
    val CAT_TAB_TITLES: Array<String> = arrayOf("幼猫专区", "成猫专区")

    fun getParentTypeByType(type: Int): Int {
        return when (type) {
            SMALL_DOG, BIG_DOG, YOUNG_DOG -> DOG
            else -> CAT
        }
    }

    fun getPositionByType(type: Int): Int {
        val parentType = getParentTypeByType(type)
        return when (parentType) {
            DOG -> DOG_TABS.indexOf(type)
            CAT -> CAT_TABS.indexOf(type)
            else -> 0
        }
    }
}