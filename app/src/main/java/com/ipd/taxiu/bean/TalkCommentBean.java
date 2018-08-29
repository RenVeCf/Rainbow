package com.ipd.taxiu.bean;

import java.util.List;

public class TalkCommentBean {

    public int ANSWER_ID;
    public int QUESTION_ID;
    public int USER_ID;
    public String CONTENT;
    public int PRAISE;
    public int REPLY;
    public int BROWSE;
    public String CREATETIME;
    public int STATUS;
    public int IS_PRAISE;
    public String NICKNAME;
    public String LOGO;
    public REPLYDATABean REPLY_DATA;


    public static class REPLYDATABean {
        public int SHOW_MORE;
        public List<CommentReplyBean> REPLY_LIST;

    }
}
