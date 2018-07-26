package com.ipd.taxiu.bean;

import java.util.List;

public class FlashSaleProductBean {
    public boolean isStart = false;
    public ProductBean cheapestProduct;
    public List<ProductBean> productList;


    public FlashSaleProductBean() {
    }

    public FlashSaleProductBean(boolean isStart) {
        this.isStart = isStart;
    }
}
