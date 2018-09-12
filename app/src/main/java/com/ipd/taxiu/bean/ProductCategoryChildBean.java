package com.ipd.taxiu.bean;

import java.util.List;

public class ProductCategoryChildBean {

    public int TYPE_ID;
    public String TYPE_NAME;
    public int CATEGORY;
    public String ICON;
    public String LOGO;
    public String PIC;
    public int KIND;
    public String BRAND_IDS;
    public String CONTENT;
    public String URL;
    public int PRODUCT_ID;
    public int SORT;
    public String CREATETIME;
    public int STATUS;
    public INFODATABean INFO_DATA;


    public static class INFODATABean {
        public List<TIPBean> TIP_LIST;
        public List<StoreIndexBrandBean> BRAND_LIST;

    }

    public static class TIPBean {
        public String TIP_NAME;
        public String ICON;

    }
}
