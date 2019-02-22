package com.ipd.rainbow.bean;

public class ProductExpertScreenBean {


    /**
     * TYPE : 2
     * COMMON_ID : 17
     * COMMON_NAME : 首饰1
     */

    public int TYPE;
    public int COMMON_ID;
    public String COMMON_NAME;
    public boolean isChecked = false;

    @Override
    public String toString() {
        return "{COMMON_ID=" + COMMON_ID + ", COMMON_NAME='" + COMMON_NAME + "}";
    }
}
