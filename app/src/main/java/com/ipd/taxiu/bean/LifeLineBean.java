package com.ipd.taxiu.bean;

import com.google.gson.annotations.SerializedName;

public class LifeLineBean {


    @SerializedName("CURREN_TTIME")
    public String date;
    public int DAY_ID;
    public String CONTENT;
    public int MONTH_NUM;
    public int DAY_NUM;

    @Override
    public String toString() {
        return "LifeLineBean{" +
                "date='" + date + '\'' +
                ", DAY_ID=" + DAY_ID +
                ", CONTENT='" + CONTENT + '\'' +
                ", MONTH_NUM=" + MONTH_NUM +
                ", DAY_NUM=" + DAY_NUM +
                '}';
    }
}
