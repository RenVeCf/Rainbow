package com.ipd.rainbow.bean;

public class ProductBean {
    public boolean isNew = false;
    public int ORDER_ID;
    public int ORDER_DETAIL_ID;
    public int PRODUCT_ID;
    public String PROCUCT_NAME;
    public String PRODUCT_NUMBER;
    public String BRAND;
    public String LOGO;
    public int FORM_ID;
    public int REPLY;
    public String NET_CONTENT;
    public String CURRENT_PRICE;
    public String PRICE;
    public String REFER_PRICE;
    public String DEAL_PRICE;
    public String FORM_GROUP_PRICE;
    public String STOCK;
    public String START_TIME;
    public String END_TIME;
    public String BUYNUM;
    public String BUY_NUM;
    public String FORM_BUYNUM;
    public String TASTE;
    public int KIND;
    public int STATUS;
    public float star;

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
