package com.ipd.rainbow.bean;

import java.util.List;

public class ProductModelResult extends BaseResult<List<ProductModelResult.ProductModelBean>> {

    public static class ProductModelBean {


        /**
         * FORM_ID : 4
         * PRODUCT_ID : 2
         * LOGO : /upload/default/product.png
         * CURRENT_PRICE : 102.6
         * PRICE : 120
         * DISCOUNT : 0.9
         * NET_CONTENT : 1
         * NORM : 所有人
         * STOCK : 100
         */

        public int FORM_ID;
        public int PRODUCT_ID;
        public String LOGO;
        public double CURRENT_PRICE;
        public int PRICE;
        public double DISCOUNT;
        public int NET_CONTENT;
        public String NORM;
        public int STOCK;

    }
}
