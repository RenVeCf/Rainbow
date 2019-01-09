package com.ipd.rainbow.bean;

/**
 * Created by Miss on 2018/7/24
 */
public class SystemMessageBean {
    private int orderType;

    public SystemMessageBean(int orderType) {
        this.orderType = orderType;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
}
