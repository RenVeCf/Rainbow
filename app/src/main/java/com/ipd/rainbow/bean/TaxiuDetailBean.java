package com.ipd.rainbow.bean;

import java.util.List;

public class TaxiuDetailBean {

    /**
     * SHOW_ID : 1
     * USER_ID : 1
     * CATEGORY : 1
     * IS_CHOSEN : 0
     * TIP : 阿拉斯加
     * TYPE : 1
     * CONTENT : 我的发布内容我的发布内容我的发布内容我的发布内容
     * LOGO :
     * URL :
     * PICS : 1.png;2.png
     * PRAISE : 0
     * BROWSE : 0
     * SORT : 1
     * STATUS : 1
     * CREATETIME : 2018-08-17 11:15:21
     * User : {"USER_ID":1,"NICKNAME":"主人_TXCW2954","LOGO":"http://pic.csjc19.com/upload/default/logo.png","TAG":"标签1","CREATETIME":"2018-07-31 16:54:21","IS_ATTEN":0,"IS_SELF":1}
     * TipList : [{"SHOW_TIP_ID":1,"TIP":"阿拉斯加","CATEGORY":1,"SORT":1,"STATUS":1,"CREATETIME":"2018-08-15 13:24:53"},{"SHOW_TIP_ID":2,"TIP":"柴犬","CATEGORY":1,"SORT":2,"STATUS":1,"CREATETIME":"2018-08-15 13:25:53"},{"SHOW_TIP_ID":3,"TIP":"金毛","CATEGORY":1,"SORT":3,"STATUS":1,"CREATETIME":"2018-08-15 13:25:53"}]
     * IS_PRAISE : 0
     * IS_COLLECT : 0
     */

    public int SHOW_ID;
    public int USER_ID;
    public int CATEGORY;
    public int IS_CHOSEN;
    public String TIP;
    public int TYPE;
    public String CONTENT;
    public String LOGO;
    public String URL;
    public String PIC;
    public int PRAISE;
    public int BROWSE;
    public int SORT;
    public int STATUS;
    public String CREATETIME;
    public UserBean User;
    public int IS_PRAISE;
    public int IS_COLLECT;
    public int REPLY;
    public List<TaxiuLableBean> TipList;
    public List<String> ShowTipList;
    public int COMMENT_NUM;


}
