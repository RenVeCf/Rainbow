package com.ipd.taxiu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuBean {
    @SerializedName("NAME")
    public String menu;
    @SerializedName("AREA_LIST")
    public List<MenuCategoryBean> list;
    public int AREA_TYPE_ID;
    public int SHOP_TYPE_ID;
    public int PARENT;
    public String LOGO;

}
