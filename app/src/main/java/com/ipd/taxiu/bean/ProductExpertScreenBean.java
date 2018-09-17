package com.ipd.taxiu.bean;

import java.util.List;

public class ProductExpertScreenBean {

    /**
     * MODULE_ID : 1
     * NAME : 冠能
     */
    public String TITLE;
    public List<ScreenInfo> LIST;

    public static class ScreenInfo {

        public int MODULE_ID;
        public String NAME;
        public String MIN_PRICE;
        public String MAX_PRICE;
    }

}
