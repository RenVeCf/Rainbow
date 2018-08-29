package com.ipd.taxiu.bean;

import java.util.List;

public class TaxiuCommentBean {


    /**
     * COMMENT_ID : 6
     * SHOW_ID : 1
     * USER_ID : 2
     * CONTENT : 我的评论内容我的评论内容我的评论内容我的评论内容
     * PIC : 1.png;2.png
     * PRAISE : 0
     * BROWSE : 0
     * REPLY : 1
     * CREATETIME : 2018-08-20 17:01:02
     * IS_PRAISE : 0
     * NICKNAME : QQ
     * LOGO : upload/default/logo.png
     * REPLY_DATA : {"REPLY_LIST":[{"REPLY_ID":8,"USER_ID":1,"TARGET_ID":0,"CONTENT":"我的回复内容","CREATETIME":"2018-08-20 17:03:00","NICKNAME":"主人_TXCW2954","TARGET_NICKNAME":""}],"SHOW_MORE":0}
     */

    public int COMMENT_ID;
    public int SHOW_ID;
    public int USER_ID;
    public String CONTENT;
    public String PIC;
    public int PRAISE;
    public int BROWSE;
    public int REPLY;
    public String CREATETIME;
    public int IS_PRAISE;
    public String NICKNAME;
    public String LOGO;
    public REPLYDATABean REPLY_DATA;


    public static class REPLYDATABean {
        /**
         * REPLY_LIST : [{"REPLY_ID":8,"USER_ID":1,"TARGET_ID":0,"CONTENT":"我的回复内容","CREATETIME":"2018-08-20 17:03:00","NICKNAME":"主人_TXCW2954","TARGET_NICKNAME":""}]
         * SHOW_MORE : 0
         */

        public int SHOW_MORE;
        public List<REPLYLISTBean> REPLY_LIST;


        public static class REPLYLISTBean {
            /**
             * REPLY_ID : 8
             * USER_ID : 1
             * TARGET_ID : 0
             * CONTENT : 我的回复内容
             * CREATETIME : 2018-08-20 17:03:00
             * NICKNAME : 主人_TXCW2954
             * TARGET_NICKNAME : 
             */

            public int REPLY_ID;
            public int USER_ID;
            public int TARGET_ID;
            public String CONTENT;
            public String CREATETIME;
            public String NICKNAME;
            public String TARGET_NICKNAME;

        }
    }
}
