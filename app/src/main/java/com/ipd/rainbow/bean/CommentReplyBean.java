package com.ipd.rainbow.bean;

import com.google.gson.annotations.SerializedName;

public class CommentReplyBean {


    /**
     * REPLY_ID : 5
     * USER_ID : 2
     * PARENT : 1
     * TARGET_ID : 1
     * CONTENT : 我的回复内容
     * PRAISE : 0
     * BROWSE : 0
     * CREATETIME : 2018-08-22 11:54:34
     * NICKNAME : QQ
     * LOGO : upload/default/logo.png
     * TARGET_NICKNAME : 主人_TXCW2954
     * IS_PRAISE : 0
     * REPLY_DATA : {}
     */

    public int REPLY_ID;
    public int USER_ID;
    public int PARENT;
    public int TARGET_ID;
    public int PRAISE;
    public int BROWSE;
    public String CREATETIME;
    public String LOGO;
    public int IS_PRAISE;
    @SerializedName("CONTENT")
    public String content;
    @SerializedName("NICKNAME")
    public String userName;
    @SerializedName("TARGET_NICKNAME")
    public String replyName;

}
