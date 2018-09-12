package com.ipd.taxiu.bean;

public class BannerBean {
    public int res;
    /**
     * BANNER_ID : 1
     * TYPE : 2
     * LOGO : /upload/image/20180819/banner.png
     * CATEGORY : 1
     * URL : http://www.baidu.com/
     * CONTENT :
     * SORT : 1
     * CREATETIME : 2018-08-22 14:16:18
     * STATUS : 1
     */

    public int BANNER_ID;
    public int TYPE;
    public String LOGO;
    public int CATEGORY;
    public String URL;
    public String CONTENT;
    public int SORT;
    public String CREATETIME;
    public int STATUS;

    public BannerBean(int res) {
        this.res = res;
    }

    public BannerBean() {
    }

    public BannerBean(String LOGO) {
        this.LOGO = LOGO;
    }
}
