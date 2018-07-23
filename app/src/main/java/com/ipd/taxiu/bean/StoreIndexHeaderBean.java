package com.ipd.taxiu.bean;

import com.ipd.taxiu.R;

import java.util.ArrayList;
import java.util.List;

public class StoreIndexHeaderBean {
    public int type;
    public List<BannerBean> bannerList;
    public List<BannerBean> smallBannerList;
    public List<StoreIndexCategoryBean> categoryList;

    public StoreIndexHeaderBean(int type) {
        this.type = type;
        bannerList = new ArrayList<>();
        smallBannerList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bannerList.add(new BannerBean(R.mipmap.store_special_banner));
            smallBannerList.add(new BannerBean(R.mipmap.store_special_banner));
        }

        categoryList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            categoryList.add(new StoreIndexCategoryBean());
        }
    }
}
