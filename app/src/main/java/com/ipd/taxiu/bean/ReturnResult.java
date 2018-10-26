package com.ipd.taxiu.bean;

public class ReturnResult extends BaseResult<ReturnResult.ReturnExpressInfoBean> {

    public String info;

    public static class ReturnExpressInfoBean {

        /**
         * ABOUT_US_ID : 1
         * COMPANY : 它嗅宠物
         * LOGO :
         * CONTENT : 内容内容
         * PHONE : 1311111111
         * CUSTOMER_TEL : 4000000000
         * WORK_TIME : 上午9:00~下午18:00
         * POST_NAME : 它嗅宠物售后
         * POST_FEE : 平台支付
         * POST_ADDRESS : 浙江省杭州市余杭区星桥街道博旺街道68号
         * CREATETIME : 2018-09-25 10:12:05
         */

        public int ABOUT_US_ID;
        public String COMPANY;
        public String LOGO;
        public String CONTENT;
        public String PHONE;
        public String CUSTOMER_TEL;
        public String WORK_TIME;
        public String POST_NAME;
        public String POST_FEE;
        public String POST_ADDRESS;
        public String CREATETIME;

    }
}

