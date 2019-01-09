package com.ipd.rainbow.bean;

public class ReturnReasonBean {

    /**
     * REFUND_TYPE_ID : 1
     * NAME : 买错了
     * SORT : 1
     * CREATETIME : 2018-10-10 13:56:09
     * STATUS : 1
     */

    public int REFUND_TYPE_ID;
    public String NAME;
    public int SORT;
    public String CREATETIME;
    public int STATUS;

    @Override
    public String toString() {
        return NAME;
    }
}
