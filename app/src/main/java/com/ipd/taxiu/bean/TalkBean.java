package com.ipd.taxiu.bean;

import java.io.Serializable;

public class TalkBean implements Serializable{

    /**
     * QUESTION_ID : 1
     * USER_ID : 16
     * IS_CHOSEN : 1
     * CONTENT : 如何有效除螨
     * SCORE : 100
     * COLLECT : 0
     * BROWSE : 1
     * REPLY : 0
     * PRAISE : 0
     * SORT : 1
     * CREATETIME : 2018-08-27 10:45:06
     * STATUS : 3
     * IS_SURE : 0
     * User : {"USER_ID":16,"NICKNAME":"哈哈","LOGO":"/upload/default/logo.png","TAG":"1213","CREATETIME":"2018-08-23 15:18:18","IS_ATTEN":0,"IS_SELF":0}
     * IS_PRAISE : 0
     * IS_COLLECT : 0
     * ANSWER_DATA : {"ANSWER_ID":1,"QUESTION_ID":1,"USER_ID":15,"CONTENT":"对的","PRAISE":0,"REPLY":0,"BROWSE":12,"CREATETIME":2018,"STATUS":1,"NICKNAME":"qqq","LOGO":"/upload/default/logo.png"}
     */

    public int QUESTION_ID;
    public int USER_ID;
    public int IS_CHOSEN;
    public String CONTENT;
    public int SCORE;
    public int COLLECT;
    public int BROWSE;
    public int REPLY;
    public int PRAISE;
    public int SORT;
    public String CREATETIME;
    public String LOGO;
    public String NICKNAME;
    public int STATUS;
    public int IS_SURE;
    public UserBean User;
    public int IS_PRAISE;
    public int IS_COLLECT;
    public ANSWERDATABean ANSWER_DATA;


    public static class UserBean {
        /**
         * USER_ID : 16
         * NICKNAME : 哈哈
         * LOGO : /upload/default/logo.png
         * TAG : 1213
         * CREATETIME : 2018-08-23 15:18:18
         * IS_ATTEN : 0
         * IS_SELF : 0
         */

        public int USER_ID;
        public String NICKNAME;
        public String LOGO;
        public String TAG;
        public String CREATETIME;
        public int IS_ATTEN;
        public int IS_SELF;

    }

    public static class ANSWERDATABean {
        /**
         * ANSWER_ID : 1
         * QUESTION_ID : 1
         * USER_ID : 15
         * CONTENT : 对的
         * PRAISE : 0
         * REPLY : 0
         * BROWSE : 12
         * CREATETIME : 2018
         * STATUS : 1
         * NICKNAME : qqq
         * LOGO : /upload/default/logo.png
         */

        public int ANSWER_ID;
        public int QUESTION_ID;
        public int USER_ID;
        public String CONTENT;
        public int PRAISE;
        public int REPLY;
        public int BROWSE;
        public String CREATETIME;
        public int STATUS;
        public String NICKNAME;
        public String LOGO;

    }
}
