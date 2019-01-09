package com.ipd.rainbow.bean;

public class ReturnDetailBean {

    /**
     * REFUND_ID : 1
     * ORDER_ID : 19
     * ORDER_DETAIL_ID : 8
     * USER_ID : 1
     * CATEGORY : 2
     * APPLY_NUM : 1
     * REASON : 买错了
     * CONTENT : 内容
     * PIC :
     * CREATETIME : 2018-10-16 13:12:56
     * CHECK_INFO :
     * CHECKTIME : 2018-10-16 13:12:56
     * STATUS : 2
     * ORDER_DETAIL : {"ORDER_DETAIL_ID":8,"ORDER_ID":19,"PRODUCT_ID":1,"FORM_ID":1,"LOGO":"/upload/shop/pro.png","PROCUCT_NAME":"奶糕","KIND":2,"NUM":100,"REFUND_NUM":0,"CURRENT_PRICE":10,"PRICE":10,"NET_CONTENT":"1.50","TASTE":"口味:混合口味","STATUS":3,"BUY_NUM":100,"APPLY_NUM":1}
     * REFUND_DETAIL : {"REFUND_DETAIL_ID":1,"REFUND_ID":1,"USER_ID":1,"POST_NUM":"1111112222","POST_COMPANY":"申通快递","RECEIVE_NAME":"","RECEIVE_PHONE":"","PROV":"","CITY":"","DIST":"","ADDRESS":"","POSTWAY":0,"POST_FEE":0,"STATUS":1,"CREATETIME":"2018-10-17 14:16:58"}
     * ORDER_NO : 1539326980946
     * ORDER_PAY_FEE : 10
     * ORDER_CREATETIME : 2018-10-12 14:49:40
     * ORDER_PAYTIME : 2018-10-12 14:49:40
     * ORDER_SEND_TIME :
     * ORDER_RECEIPT_TIME :
     * ORDER_STATUS : 3
     */

    public int REFUND_ID;
    public int ORDER_ID;
    public int ORDER_DETAIL_ID;
    public int USER_ID;
    public int CATEGORY;
    public int APPLY_NUM;
    public String REASON;
    public String CONTENT;
    public String PIC;
    public String CREATETIME;
    public String CHECK_INFO;
    public String CHECKTIME;
    public int STATUS;
    public ProductBean ORDER_DETAIL;
    public ReturnBean REFUND_DETAIL;
    public String ORDER_NO;
    public String ORDER_PAY_FEE;
    public String ORDER_CREATETIME;
    public String ORDER_PAYTIME;
    public String ORDER_SEND_TIME;
    public String ORDER_RECEIPT_TIME;
    public int ORDER_STATUS;


}
