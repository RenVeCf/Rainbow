package com.ipd.taxiu.bean;

import java.util.List;

public class ProductDetailBean {


    public int PRODUCT_ID;
    public String PROCUCT_NAME;
    public String PRODUCT_NUMBER;
    public int KIND;
    public int PULL_CATEGORY;
    public int SHOP_TYPE;
    public String BRAND;
    public int CATEGORY;
    public String LOGO;
    public int AREA;
    public int AREA_CATEGORY;
    public String THING_TYPE;
    public String APPLY;
    public String SIZE;
    public String PET_TYPE;
    public String CONTENT;
    public String PIC;
    public String COUNTRY;
    public String SEND_PROV;
    public String SEND_CITY;
    public String ORIGIN;
    public String SHELF_LIFE;
    public String VALID_TIME;
    public String AGE;
    public int POST_FEE;
    public int BUYNUM;
    public int COLLECT;
    public int REPLY;
    public int REMIND_NUM;
    public String START_TIME;
    public String END_TIME;
    public String LINK_PRODUCT_ID;
    public String GROUP_PRICE;
    public int SORT;
    public int STATUS;
    public String CREATETIME;
    public int FORM_ID;
    public String NET_CONTENT;
    public String TASTE;
    public String CURRENT_PRICE;
    public String PRICE;
    public String REFER_PRICE;
    public String DEAL_PRICE;
    public String FORM_GROUP_PRICE;
    public int STOCK;
    public List<COUPONLISTBean> COUPON_LIST;
    public List<ProductBean> GROUP_LIST;
    public int RUSH_STATE;
    public String SYS_TIME;
    public String SYS_TIME_STAMP;
    public String START_TIME_STAMP;
    public String END_TIME_STAMP;
    public int IS_COLLECT;


    public static class COUPONLISTBean {


        public int COUPON_ID;
        public int KIND;
        public int CATEGORY;
        public String LOGO;
        public String PRICE;
        public String SATISFY_PRICE;
        public int SCORE;
        public int PRODUCT_ID;
        public String CONTENT;
        public int TYPE;
        public String START_TIME;
        public String END_TIME;
        public int SORT;
        public String CREATETIME;
        public int STATUS;

    }
}
