package com.ipd.taxiu.bean;

import com.ipd.taxiu.R;

import java.util.ArrayList;
import java.util.List;

public class StoreSecondIndexHeaderBean {
    public List<BannerBean> bannerList;
    public List<StoreIndexCategoryBean> categoryList;

    public StoreSecondIndexHeaderBean() {
        bannerList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bannerList.add(new BannerBean(R.mipmap.store_special_banner));
        }

        categoryList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            categoryList.add(new StoreIndexCategoryBean());
        }
    }
}
