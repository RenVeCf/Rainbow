package com.ipd.rainbow.bean;

import java.util.List;

public class StoreIndexBean {
    public static int DOG = 1, CAT = 2;

    public int type;

    public StoreIndexHeaderBean headerInfo;
    public List<StoreIndexSpecialBean> specialList;
    public List<ProductBean> productList;

    public StoreIndexBean(int type) {
        this.type = type;
    }

//    public void buildSpecialList() {
//        specialList = new ArrayList<>();
//        if (type == DOG) {
//            specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "狗粮专区"));
//            specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "零食专区"));
//            specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "保健专区"));
//            specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "清洁专区"));
//            specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "日用专区"));
//            specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "卧具专区"));
//            specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "服饰专区"));
//            specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "玩具专区"));
//        } else {
//            specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "猫粮专区"));
//            specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "零食专区"));
//            specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "保健专区"));
//            specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "厕所猫砂"));
//            specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "清洁专区"));
//            specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "卧具专区"));
//            specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "玩具专区"));
//            specialList.add(new StoreIndexSpecialBean(R.mipmap.special_dog_food, "日用专区"));
//        }
//    }
//
//    public void buildProductList() {
//        productList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            productList.add(new ProductBean());
//        }
//    }


}
