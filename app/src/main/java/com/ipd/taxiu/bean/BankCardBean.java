package com.ipd.taxiu.bean;

/**
 * Created by Miss on 2018/8/6
 */
public class BankCardBean {
    private int iconRes;
    private String title;

    public BankCardBean(int iconRes,String title) {
        this.iconRes = iconRes;
        this.title = title;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
