package com.ipd.rainbow.bean;

import java.io.Serializable;
import java.util.List;

public class TaxiuBean implements Serializable{

    /**
     * SHOW_ID : 1
     * USER_ID : 1
     * CATEGORY : 1
     * IS_CHOSEN : 1
     * TIP : 阿拉斯加
     * TYPE : 2
     * CONTENT : 我的发布内容我的发布内容我的发布内容我的发布内容
     * LOGO : 
     * URL : 
     * PIC : upload/default/dog.png;upload/default/cat.png
     * COLLECT : 1
     * REPLY : 0
     * PRAISE : 1
     * BROWSE : 9
     * SORT : 1
     * STATUS : 3
     * CREATETIME : 2018-08-17 11:15:21
     * USER_NICKNAME : 主人_TXCW2954
     * USER_LOGO : upload/default/logo.png
     */
    public boolean isMine = false;
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
    public int COLLECT;
    public int REPLY;
    public int PRAISE;
    public int BROWSE;
    public int SORT;
    public int STATUS;
    public String CREATETIME;
    public String USER_NICKNAME;
    public String USER_LOGO;
    public List<String> ShowTipList;

}
