package com.ipd.rainbow.bean;

import java.util.List;

/**
 * Created by Miss on 2018/7/28
 */
public class GroupBean {
    /**
     * TEAM_ID : 1
     * ORDER_ID : 101
     * USER_ID : 1
     * ORDER_NO : 1540793324759
     * PAYWAY : 3
     * PAYABLE_FEE : 10
     * PAY_FEE : 10
     * PRODUCT_LIST : [{"ORDER_DETAIL_ID":107,"ORDER_ID":101,"PRODUCT_ID":6,"FORM_ID":49,"LOGO":"/upload/product/20181026/a88crhcj6tuvbeir4rbamdup3rbq9gt8.jpg","PROCUCT_NAME":"法国皇家ROYAL CANIN 小型犬离乳期奶糕1kg MIS30","KIND":6,"NUM":0,"REFUND_NUM":0,"CURRENT_PRICE":64.5,"PRICE":64.5,"NET_CONTENT":"1111","TASTE":"口味:不辣的","STATUS":3,"BUY_NUM":0}]
     * TEAM_NUM : 10
     * JOIN_NUM : 0
     * NOT_ENOUGH : 10
     * TEAM_STATUS : 0
     */

    public int TEAM_ID;
    public int ORDER_ID;
    public int USER_ID;
    public String ORDER_NO;
    public int PAYWAY;
    public String PAYABLE_FEE;
    public String PAY_FEE;
    public int TEAM_NUM;
    public int JOIN_NUM;
    public int TEAM_STATUS;
    public List<ProductBean> PRODUCT_LIST;

}
