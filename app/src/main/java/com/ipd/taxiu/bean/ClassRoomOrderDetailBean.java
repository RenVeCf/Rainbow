package com.ipd.taxiu.bean;

public class ClassRoomOrderDetailBean {


    /**
     * ORDER_ID : 1
     * USER_ID : 0
     * CLASS_ROOM_ID : 1
     * ORDER_NO : 1537172858722121
     * PAYWAY : 1
     * PAY_FEE : 100
     * PAYTIME : 2018-09-29 15:02:24
     * STATUS : 2
     * CLASS_DATA : {"ORDER_DETAIL_ID":1,"ORDER_ID":1,"CLASS_ROOM_ID":1,"TITLE":"母猪的产后护理","LOGO":"/upload/shop/pic.png","IS_TOP":1,"TEACHER":"王老师","BEGIN_TIME":"2018.10.01 12:00","END_TIME":"2018.10.01 14:00","TIME_LENGTH":"2h","PRICE":100,"CONTENT":"介绍介绍介绍1","TWO_CODE":"/upload/default/two_code.png","CLASS_STATE":1}
     */

    public int ORDER_ID;
    public int USER_ID;
    public int CLASS_ROOM_ID;
    public String ORDER_NO;
    public int PAYWAY;
    public String PAY_FEE;
    public String PAYTIME;
    public int STATUS;
    public CLASSDATABean CLASS_DATA;

    public static class CLASSDATABean {
        /**
         * ORDER_DETAIL_ID : 1
         * ORDER_ID : 1
         * CLASS_ROOM_ID : 1
         * TITLE : 母猪的产后护理
         * LOGO : /upload/shop/pic.png
         * IS_TOP : 1
         * TEACHER : 王老师
         * BEGIN_TIME : 2018.10.01 12:00
         * END_TIME : 2018.10.01 14:00
         * TIME_LENGTH : 2h
         * PRICE : 100
         * CONTENT : 介绍介绍介绍1
         * TWO_CODE : /upload/default/two_code.png
         * CLASS_STATE : 1
         */

        public int ORDER_DETAIL_ID;
        public int ORDER_ID;
        public int CLASS_ROOM_ID;
        public String TITLE;
        public String LOGO;
        public int IS_TOP;
        public String TEACHER;
        public String BEGIN_TIME;
        public String END_TIME;
        public String TIME_LENGTH;
        public int PRICE;
        public String CONTENT;
        public String TWO_CODE;
        public int CLASS_STATE;

    }
}
