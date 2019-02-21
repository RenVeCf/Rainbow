package com.ipd.rainbow.bean;

public class CommentDetailBean {

    /**
     * COMMENT_ID : 1
     * SHOW_ID : 1
     * USER_ID : 2
     * CONTENT : 我的评论内容我的评论内容我的评论内容我的评论内容
     * PICS : 1.png;2.png
     * COLLECT : 0
     * REPLY : 5
     * PRAISE : 1
     * BROWSE : 0
     * CREATETIME : 2018-08-20 13:29:40
     * User : {"USER_ID":2,"NICKNAME":"QQ","LOGO":"upload/default/logo.png","TAG":"标签2","CREATETIME":"2018-08-10 13:58:42","IS_ATTEN":0,"IS_SELF":0}
     * IS_PRAISE : 0
     */

    public int COMMENT_ID;
    public int SHOW_ID;
    public int USER_ID;
    public String CONTENT;
    public String PIC;
    public int COLLECT;
    public int REPLY;
    public int PRAISE;
    public int BROWSE;
    public String CREATETIME;
    public UserBean User;
    public int IS_PRAISE;

}
