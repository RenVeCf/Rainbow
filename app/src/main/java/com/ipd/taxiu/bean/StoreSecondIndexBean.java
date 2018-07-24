package com.ipd.taxiu.bean;

import com.ipd.taxiu.R;

import java.util.ArrayList;
import java.util.List;

public class StoreSecondIndexBean {
    public StoreSecondIndexHeaderBean headerInfo;
    public List<StoreIndexSpecialBean> specialList;
    public StoreIndexVideoBean recommendVideo;
    public List<ProductBean> productList;

    public StoreSecondIndexBean() {
    }

    public void buildSpecialList() {
        specialList = new ArrayList<>();
        specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "狗粮专区"));
        specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "零食专区"));
        specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "保健专区"));
        specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "清洁专区"));
        specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "日用专区"));
        specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "卧具专区"));
        specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "服饰专区"));
        specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "玩具专区"));
    }

    public void buildProductList() {
        productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            productList.add(new ProductBean());
        }
    }


}
