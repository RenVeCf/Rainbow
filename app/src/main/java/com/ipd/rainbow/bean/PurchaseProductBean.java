package com.ipd.rainbow.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class PurchaseProductBean {

    public int PRODUCT_ID;
    @SerializedName("NAME")
    public String PROCUCT_NAME;
    public String PRODUCT_NUMBER;
    public int KIND;
    public String BRAND;
    public String LOGO;
    public int FORM_ID;
    public String NET_CONTENT;
    public String CURRENT_PRICE;
    public String PRICE;
    public String PRICE_AREA;
    public int STOCK;
    public String START_TIME;
    public String END_TIME;
    public int TEAM_NUM;
    public int JOIN_NUM;
    public int JOIN_STATUS;
    public String SYS_TIME;
    public String SYS_TIME_STAMP;
    public String START_TIME_STAMP;
    public String END_TIME_STAMP;
    
    public long getStartTime(){
        if (TextUtils.isEmpty(START_TIME_STAMP))return 0L;
        return Long.parseLong(START_TIME_STAMP);
    }
    public long getEndTime(){
        if (TextUtils.isEmpty(END_TIME_STAMP))return 0L;
        return Long.parseLong(END_TIME_STAMP);
    }
}
