package com.ipd.rainbow.bean;

import java.util.List;

public class StoreIndexHeaderBean {
    public int type;
    public List<BannerBean> bannerList;
    public List<BannerBean> smallBannerList;
    public List<StoreIndexCategoryBean> categoryList;
    public List<StoreAreaBean> areaList;


    public StoreIndexHeaderBean(int type) {
        this.type = type;

    }
}
