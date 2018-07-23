package com.ipd.taxiu.bean;

/**
 * Created by Miss on 2018/7/23
 */
public class ReturnBean {
    //退款类型 1.退款  2.退货
    private int returnType;

    //退货状态 2代发货 3待收货 4已完成
    private int returnStatus;

    private String statusStr;

    public int getReturnType() {
        return returnType;
    }

    public int getReturnStatus() {
        return returnStatus;
    }

    public ReturnBean(String statusStr,int returnType, int returnStatus) {
        this.statusStr = statusStr;
        this.returnType = returnType;
        this.returnStatus = returnStatus;
    }

    public void setReturnType(int returnType) {
        this.returnType = returnType;
    }

    public void setReturnStatus(int returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }
}
