package com.ipd.taxiu.bean;

/**
 * Created by Miss on 2018/7/28
 */
public class GroupBean {
    //1.待成团  2.已成团  3.未成团
    private int status;

    public GroupBean(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
