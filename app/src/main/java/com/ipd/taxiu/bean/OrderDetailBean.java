package com.ipd.taxiu.bean;

import java.util.List;

/**
 * Created by Miss on 2018/7/20
 */
public class OrderDetailBean {

    /**
     * ORDER_ID : 19
     * USER_ID : 1
     * ORDER_NO : 1539326980946
     * PAYWAY : 3
     * RECEIVE_NAME : 张三
     * RECEIVE_PHONE : 1311111111
     * PROV : 上海市
     * CITY : 上海
     * DIST : 青浦区
     * ADDRESS : 华徐公路888
     * PRODUCT_LIST : [{"ORDER_DETAIL_ID":8,"ORDER_ID":19,"PRODUCT_ID":1,"FORM_ID":1,"LOGO":"/upload/shop/pro.png","PROCUCT_NAME":"奶糕","KIND":2,"NUM":100,"REFUND_NUM":0,"CURRENT_PRICE":10,"PRICE":10,"NET_CONTENT":"1.50","TASTE":"口味:混合口味","STATUS":3,"BUY_NUM":100},{"ORDER_DETAIL_ID":7,"ORDER_ID":19,"PRODUCT_ID":1,"FORM_ID":2,"LOGO":"/upload/shop/pro.png","PROCUCT_NAME":"奶糕","KIND":3,"NUM":21,"REFUND_NUM":0,"CURRENT_PRICE":20,"PRICE":20,"NET_CONTENT":"2.00","TASTE":"口味:鱼肉","STATUS":3,"BUY_NUM":21}]
     * PRODUCT_NUM : 0
     * INVOICE_TYPE : 1
     * INVOICE_HEAD : 抬头
     * INVOICE_NUM : 1111111
     * TOTAL : 1420
     * PREFER_FEE : 0
     * POST_FEE : 10
     * PAY_FEE : 1430
     * POST_NUM : 
     * POST_COMPANY : 
     * CREATETIME : 2018-10-12 14:49:40
     * PAYTIME : 2018-10-12 14:49:40
     * SEND_TIME : 
     * RECEIPT_TIME : 
     * STATUS : 3
     */

    public int ORDER_ID;
    public int USER_ID;
    public String ORDER_NO;
    public int PAYWAY;
    public String RECEIVE_NAME;
    public String RECEIVE_PHONE;
    public String PROV;
    public String CITY;
    public String DIST;
    public String ADDRESS;
    public int PRODUCT_NUM;
    public int INVOICE_TYPE;
    public String INVOICE_HEAD;
    public String INVOICE_NUM;
    public int TOTAL;
    public int PREFER_FEE;
    public int POST_FEE;
    public int PAY_FEE;
    public String POST_NUM;
    public String POST_COMPANY;
    public String CREATETIME;
    public String PAYTIME;
    public String SEND_TIME;
    public String RECEIPT_TIME;
    public int STATUS;
    public List<ProductBean> PRODUCT_LIST;

}
