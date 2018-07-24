package com.ipd.taxiu.bean;

import com.ipd.taxiu.R;

import java.util.ArrayList;
import java.util.List;

public class StoreSpecialHeaderBean {
    public List<BannerBean> bannerList;
    public List<BannerBean> smallBannerList;
    public List<MenuBean> menuList;

    public StoreSpecialHeaderBean() {
        bannerList = new ArrayList<>();
        smallBannerList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bannerList.add(new BannerBean(R.mipmap.store_special_banner));
            smallBannerList.add(new BannerBean(R.mipmap.store_special_banner));
        }

        menuList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MenuBean menu = new MenuBean();
            menu.menu = "按犬种";
            menu.list = new ArrayList<>();
            menu.list.add(new MenuCategoryBean(0, R.mipmap.clothes_icon, "服饰"));
            menu.list.add(new MenuCategoryBean(0, R.mipmap.clothes_icon, "服饰"));
            menu.list.add(new MenuCategoryBean(0, R.mipmap.clothes_icon, "服饰"));
            menu.list.add(new MenuCategoryBean(0, R.mipmap.clothes_icon, "服饰"));
            menu.list.add(new MenuCategoryBean(0, R.mipmap.clothes_icon, "服饰"));
            menu.list.add(new MenuCategoryBean(0, R.mipmap.clothes_icon, "服饰"));
            menu.list.add(new MenuCategoryBean(0, R.mipmap.clothes_icon, "服饰"));
            menu.list.add(new MenuCategoryBean(0, R.mipmap.clothes_icon, "服饰"));
            menuList.add(menu);
        }
    }
}
