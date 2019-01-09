package com.ipd.rainbow.bean;

import com.google.gson.annotations.SerializedName;

public class MenuCategoryBean {
    @SerializedName("NAME")
    public String title;
    public int AREA_TYPE_ID;
    public int SHOP_TYPE_ID;
    public int PARENT;
    public String LOGO;

}
