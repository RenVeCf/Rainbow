package com.ipd.rainbow.bean;

public class CartProductBean {
    public boolean isChecked = false;

    /**
     * CART_ID : 3
     * USER_ID : 1
     * PRODUCT_ID : 1
     * FORM_ID : 2
     * NUM : 2
     * SUB_TOTAL : 40
     * PRODUCT : {"PRODUCT_ID":1,"PROCUCT_NAME":"奶糕","PRODUCT_NUMBER":"1537172614943039","STYLE":1,"KIND":1,"BRAND":"冠能","LOGO":"/upload/shop/pro.png","FORM_ID":2,"NET_CONTENT":"2.00","TASTE":"口味:鱼肉","CURRENT_PRICE":20,"PRICE":20,"REFER_PRICE":30,"BUYNUM":0}
     */

    public int CART_ID;
    public int USER_ID;
    public int PRODUCT_ID;
    public int FORM_ID;
    public int NUM;
    public String SUB_TOTAL;
    public ProductBean PRODUCT;

}
