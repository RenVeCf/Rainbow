package com.ipd.rainbow.bean;

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

        public ScreenInfo(int MODULE_ID, String NAME) {
            this.MODULE_ID = MODULE_ID;
            this.NAME = NAME;
        }

        public ScreenInfo() {
        }

        @Override
        public String toString() {
            return "ScreenInfo{" +
                    "MODULE_ID=" + MODULE_ID +
                    ", NAME='" + NAME + '\'' +
                    ", MIN_PRICE='" + MIN_PRICE + '\'' +
                    ", MAX_PRICE='" + MAX_PRICE + '\'' +
                    '}';
        }
    }

}
