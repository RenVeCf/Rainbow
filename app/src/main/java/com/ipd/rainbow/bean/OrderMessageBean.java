package com.ipd.rainbow.bean;

/**
 * Created by Miss on 2018/7/24
 */
public class OrderMessageBean {
    //订单状态 1待付款 2待发货 3 待收货 4已完成
    private int orderStatus;

    public OrderMessageBean(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
}
