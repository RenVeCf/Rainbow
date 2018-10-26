package com.ipd.taxiu.utils

object Return {


    fun getAuthStrByStatus(status: Int): String {
        return when (status) {
            1 -> "审核中"
            2 -> "审核已通过，待退款"
            3 -> "审核未通过"
            4 -> "审核已通过，已退款"
            else -> "未知"
        }
    }

}