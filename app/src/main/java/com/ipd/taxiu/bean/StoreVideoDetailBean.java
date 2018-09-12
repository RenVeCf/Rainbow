package com.ipd.taxiu.bean;

import java.util.List;

public class StoreVideoDetailBean {

    /**
     * VIDEO_ID : 1
     * TITLE : 易拉罐漏食狗玩具1
     * LOGO : /upload/shop/video.png
     * CATEGORY : 1
     * URL : http://www.baidu.com
     * TIME_LENGTH : 12:00
     * SHOP_TYPE_ID : 1
     * IS_RECOMMEND : 1
     * PRODUCT_IDS : 1,1;2,4
     * BROWSE : 2
     * SORT : 1
     * CREATETIME : 2018-09-06 13:41:02
     * STATUS : 1
     * PRODUCT_LIST : [{"PRODUCT_ID":2,"PROCUCT_NAME":"奶糕2","PRODUCT_NUMBER":"00000000002","BRAND":"贵族","LOGO":"/upload/shop/pro.png","REPLY":0,"FORM_ID":4,"NET_CONTENT":1.5,"PRICE":10,"REFER_PRICE":20,"DEAL_PRICE":0,"FORM_GROUP_PRICE":10,"FORM_BUYNUM":0},{"PRODUCT_ID":1,"PROCUCT_NAME":"奶糕1","PRODUCT_NUMBER":"00000000001","BRAND":"冠能","LOGO":"/upload/shop/pro.png","REPLY":0,"FORM_ID":1,"NET_CONTENT":1.5,"PRICE":10,"REFER_PRICE":20,"DEAL_PRICE":0,"FORM_GROUP_PRICE":10,"FORM_BUYNUM":0}]
     * VIDEO_LIST : [{"VIDEO_ID":11,"TITLE":"易拉罐漏食狗玩具11","LOGO":"/upload/shop/video.png","CATEGORY":1,"URL":"http://www.baidu.com","TIME_LENGTH":"12:00","SHOP_TYPE_ID":1,"IS_RECOMMEND":1,"PRODUCT_IDS":"1,1;2,4","BROWSE":0,"SORT":1,"CREATETIME":"2018-09-06 13:41:02","STATUS":1},{"VIDEO_ID":10,"TITLE":"易拉罐漏食狗玩具10","LOGO":"/upload/shop/video.png","CATEGORY":1,"URL":"http://www.baidu.com","TIME_LENGTH":"12:00","SHOP_TYPE_ID":1,"IS_RECOMMEND":1,"PRODUCT_IDS":"1,1;2,4","BROWSE":0,"SORT":1,"CREATETIME":"2018-09-06 13:41:02","STATUS":1}]
     */

    public int VIDEO_ID;
    public String TITLE;
    public String LOGO;
    public int CATEGORY;
    public String URL;
    public String TIME_LENGTH;
    public int SHOP_TYPE_ID;
    public int IS_RECOMMEND;
    public String PRODUCT_IDS;
    public int BROWSE;
    public int SORT;
    public String CREATETIME;
    public int STATUS;
    public List<ProductBean> PRODUCT_LIST;
    public List<StoreVideoBean> VIDEO_LIST;


}
