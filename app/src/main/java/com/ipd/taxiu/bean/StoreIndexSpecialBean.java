package com.ipd.taxiu.bean;

import java.util.ArrayList;
import java.util.List;

public class StoreIndexSpecialBean {

    public int specialRes;
    public String specialName;
    public List<String> lableList;
    public List<ProductBean> productList;

    public StoreIndexSpecialBean() {
    }

    public StoreIndexSpecialBean(int specialRes, String specialName) {
        this.specialRes = specialRes;
        this.specialName = specialName;
        this.lableList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lableList.add("品牌" + i);
        }
        this.productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            productList.add(new ProductBean());
        }
    }
}
