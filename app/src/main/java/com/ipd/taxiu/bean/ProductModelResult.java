package com.ipd.taxiu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductModelResult extends BaseResult<List<ProductModelResult.ProductModelBean>> {
    @SerializedName("data2")
    public String modelName;


    public static class ProductModelBean {

        /**
         * FORM_ID : 3
         * PRODUCT_ID : 1
         * KIND : 4
         * NET_CONTENT : 3.00
         * TASTE : 鸡肉味
         * CURRENT_PRICE : 8
         * PRICE : 8
         * REFER_PRICE : 25
         * STOCK : 100
         * IS_CHOSEN : 0
         */

        public int FORM_ID;
        public int PRODUCT_ID;
        public int KIND;
        public String NET_CONTENT;
        public String TASTE;
        public String CURRENT_PRICE;
        public String PRICE;
        public String REFER_PRICE;
        public String LOGO;
        public int STOCK;
        public int IS_CHOSEN;

    }
}
