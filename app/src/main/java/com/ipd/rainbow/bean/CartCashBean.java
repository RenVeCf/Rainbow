package com.ipd.rainbow.bean;

import java.util.List;

public class CartCashBean {

    /**
     * ADDRESS_DATA : {"ADDRESS_ID":3,"USER_ID":1,"RECIPIENT":"张三","TEL":"1311111111","PROV":"上海市","CITY":"上海","DIST":"青浦区","ADDRESS":"华徐公路888","CREATETIME":"2018-10-10 17:28:36","STATUS":2}
     * PRODUCT_LIST : [{"CART_ID":0,"USER_ID":1,"PRODUCT_ID":1,"FORM_ID":1,"NUM":2,"SUB_TOTAL":20,"PRODUCT":{"PRODUCT_ID":1,"PROCUCT_NAME":"奶糕","PRODUCT_NUMBER":"1537172614943039","STYLE":1,"KIND":2,"BRAND":"冠能","LOGO":"/upload/shop/pro.png","FORM_ID":1,"NET_CONTENT":"1.50","TASTE":"口味:混合口味","CURRENT_PRICE":10,"PRICE":10,"REFER_PRICE":20,"BUYNUM":0}}]
     * PRODUCT_NUM : 1
     * PRODUCT_TOTAL : 20
     * POST_FEE : 10
     * EXCHANGE_DATA : {}
     * COUPON_TIP :
     * PAY_FEE : 30
     * PREFER_FEE : 0
     * BALANCE : 94280
     * USE_COUPON : 1
     */

    public AddressBean ADDRESS_DATA;
    public int PRODUCT_NUM;
    public String PRODUCT_TOTAL;
    public String POST_FEE;
    public String COUPON_TIP;
    public ExchangeBean EXCHANGE_DATA;
    public String PAY_FEE;
    public String PREFER_FEE;
    public String BALANCE;
    public int USE_COUPON;
    public List<CartProductBean> PRODUCT_LIST;

}
