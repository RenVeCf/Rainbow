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
    public int ORDER_ID;
    public int ORDER_DETAIL_ID;
    public int STATUS;
    public int FORM_ID;
    public String NAME;
    public String NORM;
    public String LOGO;
    public int KIND;
    public String CURRENT_PRICE;
    public String PRICE;
    public String DISCOUNT;
    public int SALE;
    public int ASSESS;
    public int BUY_NUM;
    public String LEVEL;

    public float star;

    public boolean isNew() {
        return KIND == 2;
    }

    public boolean isSales() {
        return KIND == 1;
    }

    public String getKindStr() {
        String str = "";
        switch (KIND) {
            case 0:
                str = "普通";
                break;
            case 1:
                str = "特价";
                break;
            case 2:
                str = "上新";
                break;
            case 3:
                str = "库存";
                break;
            case 4:
                str = "团购";
                break;
        }
        return str;
    }

}
