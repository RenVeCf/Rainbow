package com.ipd.rainbow.utils

import com.ipd.rainbow.bean.OrderBean

object Order {

    //状态 2:待付款  3:待发货 4:待收货 5:待评价 6:已完成 7:退款中  8:退款关闭  9:退款成功 10:发货中
    const val PAYMENT = 2
    const val WAIT_SEND = 3
    const val WAIT_RECEIVE = 4
    const val EVALUATE = 5
    const val FINFISH = 6
    const val RETURN = 7
    const val RETURN_CLOSE = 8
    const val RETURN_SUCCESS = 9
    const val SHIPPING = 10


    interface OrderItemClickListener {
        fun onItemClick(info: OrderBean)
        fun onCancelOrder(info: OrderBean)
        fun onPayment(info: OrderBean)
        fun onExpress(info: OrderBean)
        fun onReceived(info: OrderBean)
        fun onBuyAgain(info: OrderBean)
        fun onEvaluate(info: OrderBean)
    }


    fun getOrderStrByStatus(status: Int): String {
        return when (status) {
            0 -> "已删除"
            2 -> "待付款"
            3 -> "待发货"
            4 -> "待收货"
            5 -> "待评价"
            6 -> "已完成"
            7 -> "退款中"
            8 -> "退款关闭"
            9 -> "退款成功"
            10 -> "发货中"
            else -> "未知"
        }
    }

}