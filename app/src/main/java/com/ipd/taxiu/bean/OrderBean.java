package com.ipd.taxiu.bean;

/**
 * Created by Miss on 2018/7/19
 */
public class OrderBean {
    private int status;

    public OrderBean(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
