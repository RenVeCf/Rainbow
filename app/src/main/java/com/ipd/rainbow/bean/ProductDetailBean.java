package com.ipd.rainbow.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailBean {


    public int PRODUCT_ID;
    public int ACTIVITY_ID;
    @SerializedName("NAME")
    public String PROCUCT_NAME;
    public String PRODUCT_NUMBER;
    public int KIND;
    public int PULL_CATEGORY;
    public String SHOP_TYPE;
    public String BRAND;
    public String CATEGORY;
    public String LOGO;
    public String AREA;
    public String AREA_CATEGORY;
    public String THING_TYPE;
    public String APPLY;
    public String SIZE;
    public String PET_TYPE;
    public String CONTENT;
    public String PICS;
    public String VIDEO;
    public String VIDEO_URL;
    public String COUNTRY;
    public String ADDRESS;
    public String ORIGIN;
    public String SHELF_LIFE;
    public String VALID_TIME;
    public String AGE;
    public String POST_FEE;
    public int BUYNUM;
    public int SALE;
    public int COLLECT;
    public int ASSESS;
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
    public int STOCK;
    public List<ExchangeBean> COUPON_LIST;
    public List<ProductBean> GROUP_LIST;
    public String SYS_TIME;
    public String SYS_TIME_STAMP;
    public String START_TIME_STAMP;
    public String END_TIME_STAMP;
    public String TEAM_NUM;
    public String JOIN_NUM;
    public String JOIN_STATUS;
    public int IS_COLLECT;
    public double GOOD_PERCENT;
    public List<ProductEvaluateBean> ASSESS_LIST;
    public boolean isGroup = false;


    public static class ProductEvaluateBean {


        /**
         * USER_NICKNAME : CHG5770
         * USER_LOGO : upload/user/20190219/2fcd54e4cc6a46cdbff09b9bfb8ce015.png
         * CONTENT : ljkl
         * PIC : upload/user/20190219/8493f6859221468d86a8fcc7e2b7ee7e.jpg
         * TOTAL_SCORE : 5
         */

        public String USER_NICKNAME;
        public String USER_LOGO;
        public String CONTENT;
        public String PIC;
        public float TOTAL_SCORE;
    }
}
