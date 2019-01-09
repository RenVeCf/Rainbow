package com.ipd.rainbow.bean;

/**
 * Created by Miss on 2018/7/27
 */
public class ContactBean {
    //1.已关注  2.未关注 3.相互关注
    private int status;

    public ContactBean(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
