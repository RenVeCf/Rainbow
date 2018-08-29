package com.ipd.taxiu.bean;

import java.util.List;

public class TopicCommentReplyBean {


    /**
     * REPLY_ID : 1
     * USER_ID : 1
     * PARENT : 0
     * TARGET_ID : 0
     * CONTENT : 我的回复内容
     * PRAISE : 0
     * BROWSE : 0
     * CREATETIME : 2018-08-22 11:51:43
     * NICKNAME : 主人_TXCW2954
     * LOGO : upload/default/logo.png
     * TARGET_NICKNAME :
     * IS_PRAISE : 1
     */

    public int REPLY_ID;
    public int USER_ID;
    public int PARENT;
    public int TARGET_ID;
    public String CONTENT;
    public int PRAISE;
    public int BROWSE;
    public String CREATETIME;
    public String NICKNAME;
    public String LOGO;
    public String TARGET_NICKNAME;
    public int IS_PRAISE;
    public ReplyData REPLY_DATA;


    public static class ReplyData {
        public List<CommentReplyBean> REPLY_LIST;
        public int SHOW_MORE;
    }


}
