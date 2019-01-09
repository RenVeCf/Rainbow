package com.ipd.rainbow.bean;

public class BankTypeListBean {

    /**
     * BANK_TYPE_ID : 1
     * BANK_NAME : 中国建设银行
     * LOGO : 
     * SORT : 0
     * CREATETIME : 
     * STATUS : 0
     */

    public int BANK_TYPE_ID;
    public String BANK_NAME;
    public String LOGO;
    public int SORT;
    public String CREATETIME;
    public int STATUS;

    @Override
    public String toString() {
        return BANK_NAME;
    }

    public BankTypeListBean(int BANK_TYPE_ID, String BANK_NAME) {
        this.BANK_TYPE_ID = BANK_TYPE_ID;
        this.BANK_NAME = BANK_NAME;
    }

    public BankTypeListBean() {
    }
}
