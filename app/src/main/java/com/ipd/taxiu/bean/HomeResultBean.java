package com.ipd.taxiu.bean;

import java.util.List;

public class HomeResultBean {

    /**
     * PET : {"PET_ID":1,"USER_ID":1,"PET_TYPE_ID":1,"NICKNAME":"斯基大魔王","LOGO":"upload/default/dog.png","GENDER":1,"BIRTHDAY":"2017.01.15","SORT":0,"CATEGORY":1,"STATUS":1,"CREATETIME":"2018-08-14 18:11:41","MONTH_NUM":19,"DAY_NUM":28,"DAY_LIST":[{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":13,"CURREN_TTIME":"2018.08.28"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":14,"CURREN_TTIME":"2018.08.29"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":15,"CURREN_TTIME":"2018.08.30"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":16,"CURREN_TTIME":"2018.08.31"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":17,"CURREN_TTIME":"2018.09.01"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":18,"CURREN_TTIME":"2018.09.02"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":19,"CURREN_TTIME":"2018.09.03"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":20,"CURREN_TTIME":"2018.09.04"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":21,"CURREN_TTIME":"2018.09.05"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":22,"CURREN_TTIME":"2018.09.06"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":23,"CURREN_TTIME":"2018.09.07"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":24,"CURREN_TTIME":"2018.09.08"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":25,"CURREN_TTIME":"2018.09.09"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":26,"CURREN_TTIME":"2018.09.10"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":27,"CURREN_TTIME":"2018.09.11"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":28,"CURREN_TTIME":"2018.09.12"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":29,"CURREN_TTIME":"2018.09.13"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":30,"CURREN_TTIME":"2018.09.14"}],"SYSTEM_TTIME":"2018.09.12"}
     * BANNER_LIST : [{"BANNER_ID":1,"TYPE":1,"LOGO":"/upload/image/20180819/headerInfo.png","CATEGORY":1,"URL":"http://www.baidu.com/","CONTENT":"","SORT":1,"CREATETIME":"2018-08-22 14:16:18","STATUS":1},{"BANNER_ID":2,"TYPE":1,"LOGO":"/upload/image/20180819/banner2.png","CATEGORY":2,"URL":"","CONTENT":"<p>111111333333<\/p>","SORT":2,"CREATETIME":"2018-08-22 14:16:18","STATUS":1}]
     * SHOW_LIST : [{"SHOW_ID":8,"USER_ID":10,"CATEGORY":1,"IS_CHOSEN":1,"TIP":"阿拉斯加","TYPE":1,"CONTENT":"这是我发布的一个视频它秀","LOGO":"/upload/image/20180901/can3adgo2uplncmezxwlh6icn9h6q9jp.jpg","URL":"/upload/video/20180901/x1ql3qmwflzxdbjsigkatxdr1ive4ehx.MP4","PIC":"","COLLECT":0,"REPLY":5,"PRAISE":0,"BROWSE":150,"SORT":8,"STATUS":3,"CREATETIME":"2018-09-01 12:34:07","USER_NICKNAME":"娜可露露","USER_LOGO":"/upload/image/20180821/9dwg8aq67yytobntahecl3rg3rmyselq.jpg"}]
     * TOPIC_DATA : {"TOPIC_ID":1,"TITLE":"你家主子有什么特别的撒娇卖萌方式","LOGO":"upload/default/cat.png","CONTENT":"SFDSFDSDF","PRAISE":3,"COLLECT":3,"REPLY":6,"BROWSE":96,"IS_CHOSEN":1,"SORT":1,"STATUS":1,"CREATETIME":"2018-08-23 16:28:52"}
     * QUESTION_LIST : [{"QUESTION_ID":3,"USER_ID":1,"IS_CHOSEN":1,"CONTENT":"我的发布内容我的发布内容我的发布内容我的发布内容2","SCORE":0,"COLLECT":1,"BROWSE":103,"REPLY":4,"PRAISE":2,"SORT":3,"CREATETIME":"2018-08-28 11:15:23","STATUS":3,"IS_SURE":1,"NICKNAME":"主人_TXCW2954","LOGO":"upload/default/logo.png","ANSWER_DATA":{"ANSWER_ID":1,"QUESTION_ID":3,"USER_ID":2,"CONTENT":"我的评论内容我的评论内容我的评论内容我的评论内容","PRAISE":15,"REPLY":12,"BROWSE":32,"CREATETIME":"2018-08-28 14:27:35","STATUS":2,"NICKNAME":"QQ","LOGO":"upload/default/logo.png"}},{"QUESTION_ID":2,"USER_ID":1,"IS_CHOSEN":1,"CONTENT":"我的发布内容我的发布内容我的发布内容我的发布内容1","SCORE":100,"COLLECT":0,"BROWSE":4,"REPLY":1,"PRAISE":0,"SORT":2,"CREATETIME":"2018-08-28 11:06:44","STATUS":3,"IS_SURE":1,"NICKNAME":"主人_TXCW2954","LOGO":"upload/default/logo.png","ANSWER_DATA":{"ANSWER_ID":2,"QUESTION_ID":2,"USER_ID":2,"CONTENT":"我的评论内容我的评论内容我的评论内容我的评论内容2","PRAISE":0,"REPLY":0,"BROWSE":0,"CREATETIME":"2018-08-28 17:13:20","STATUS":2,"NICKNAME":"QQ","LOGO":"upload/default/logo.png"}}]
     * CLASS_DATA : {"CLASS_ROOM_ID":1,"TITLE":"宠物健康及心理健康咨询课程","LOGO":"upload/default/dog.png","TEACHER":"王明毅","BEGIN_TIME":"2018.09.15 16:00","END_TIME":"2018.09.15 16:30","TIME_LENGTH":"30分钟","PRICE":150,"CONTENT":"<p>内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1内容1<p>","TWO_CODE":"upload/default/two_code.png","COLLECT":3,"IS_TOP":0,"SORT":1,"CREATETIME":"2018-08-25 16:32:57","STATUS":1,"CLASS_STATE":1,"IS_COLLECT":0,"IS_BUY":0}
     */

    public PETBean PET;
    public List<BannerBean> BANNER_LIST;
    public List<TaxiuBean> SHOW_LIST;
    public TopicBean TOPIC_DATA;
    public List<TalkBean> QUESTION_LIST;
    public ClassRoomBean CLASS_DATA;


    public static class PETBean {
        /**
         * PET_ID : 1
         * USER_ID : 1
         * PET_TYPE_ID : 1
         * NICKNAME : 斯基大魔王
         * LOGO : upload/default/dog.png
         * GENDER : 1
         * BIRTHDAY : 2017.01.15
         * SORT : 0
         * CATEGORY : 1
         * STATUS : 1
         * CREATETIME : 2018-08-14 18:11:41
         * MONTH_NUM : 19
         * DAY_NUM : 28
         * DAY_LIST : [{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":13,"CURREN_TTIME":"2018.08.28"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":14,"CURREN_TTIME":"2018.08.29"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":15,"CURREN_TTIME":"2018.08.30"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":16,"CURREN_TTIME":"2018.08.31"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":17,"CURREN_TTIME":"2018.09.01"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":18,"CURREN_TTIME":"2018.09.02"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":19,"CURREN_TTIME":"2018.09.03"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":20,"CURREN_TTIME":"2018.09.04"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":21,"CURREN_TTIME":"2018.09.05"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":22,"CURREN_TTIME":"2018.09.06"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":23,"CURREN_TTIME":"2018.09.07"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":24,"CURREN_TTIME":"2018.09.08"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":25,"CURREN_TTIME":"2018.09.09"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":26,"CURREN_TTIME":"2018.09.10"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":27,"CURREN_TTIME":"2018.09.11"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":28,"CURREN_TTIME":"2018.09.12"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":29,"CURREN_TTIME":"2018.09.13"},{"DAY_ID":0,"CONTENT":"","MONTH_NUM":19,"DAY_NUM":30,"CURREN_TTIME":"2018.09.14"}]
         * SYSTEM_TTIME : 2018.09.12
         */

        public int PET_ID;
        public int USER_ID;
        public int PET_TYPE_ID;
        public String NICKNAME;
        public String LOGO;
        public int GENDER;
        public String BIRTHDAY;
        public int SORT;
        public int CATEGORY;
        public int STATUS;
        public String CREATETIME;
        public int MONTH_NUM;
        public int DAY_NUM;
        public String SYSTEM_TTIME;
        public List<LifeLineBean> DAY_LIST;

    }
}
