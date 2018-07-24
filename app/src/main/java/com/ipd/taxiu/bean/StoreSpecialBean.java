package com.ipd.taxiu.bean;

import java.util.ArrayList;
import java.util.List;

public class StoreSpecialBean {
    public StoreSpecialHeaderBean headerInfo;
    public StoreIndexVideoBean recommendVideo;
    public List<ProductBean> productList;

    public StoreSpecialBean() {
    }

    public void buildProductList() {
        productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            productList.add(new ProductBean());
        }
    }


}
