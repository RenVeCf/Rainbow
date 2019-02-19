package com.ipd.rainbow.bean;

public class ProductBean {


    /**
     * USER_ID : 1
     * PRODUCT_ID : 2
     * FORM_ID : 3
     * NAME : 商品1
     * LOGO : /upload/default/product.png
     * KIND : 2
     * CURRENT_PRICE : 85.5
     * PRICE : 100
     * DISCOUNT : 0.9
     * SALE : 0
     * ASSESS : 0
     * LEVEL : 
     */

    public int USER_ID;
    public int PRODUCT_ID;
    public int FORM_ID;
    public String NAME;
    public String LOGO;
    public int KIND;
    public String CURRENT_PRICE;
    public String PRICE;
    public String DISCOUNT;
    public int SALE;
    public int ASSESS;
    public String LEVEL;

    //暂时占位
    public boolean isNew;
    public float star;
    public String TASTE;

    public String getKindStr() {
        String str = "";
        switch (KIND) {
            case 1:
                str = "普通";
                break;
            case 2:
                str = "抢购";
                break;
            case 3:
                str = "清仓";
                break;
            case 4:
                str = "上新";
                break;
            case 5:
                str = "组合";
                break;
        }
        return str;
    }

}
