package com.ipd.taxiu.bean;

public class ReturnOrderInfoBean {

    /**
     * ORDER_DETAIL : {"ORDER_DETAIL_ID":7,"ORDER_ID":19,"PRODUCT_ID":1,"FORM_ID":2,"LOGO":"/upload/shop/pro.png","PROCUCT_NAME":"奶糕","KIND":3,"NUM":21,"REFUND_NUM":0,"CURRENT_PRICE":20,"PRICE":20,"NET_CONTENT":"2.00","TASTE":"口味:鱼肉","STATUS":3,"BUY_NUM":21}
     * ORDER_STATUS : 3
     * CONTENT : 申请退款申请退款申请退款申请退款申请退款申请退款申请退款申请退款
     * ABOUT_US : {"ABOUT_US_ID":1,"COMPANY":"它嗅宠物","LOGO":"","CONTENT":"内容内容","PHONE":"1311111111","CUSTOMER_TEL":"4000000000","WORK_TIME":"上午9:00~下午18:00","POST_NAME":"它嗅宠物售后","POST_FEE":"平台支付","POST_ADDRESS":"浙江省杭州市余杭区星桥街道博旺街道68号","CREATETIME":"2018-09-25 10:12:05"}
     */

    public ORDERDETAILBean ORDER_DETAIL;
    public int ORDER_STATUS;
    public String CONTENT;
    public AboutUsBean ABOUT_US;

    public static class ORDERDETAILBean {
        /**
         * ORDER_DETAIL_ID : 7
         * ORDER_ID : 19
         * PRODUCT_ID : 1
         * FORM_ID : 2
         * LOGO : /upload/shop/pro.png
         * PROCUCT_NAME : 奶糕
         * KIND : 3
         * NUM : 21
         * REFUND_NUM : 0
         * CURRENT_PRICE : 20
         * PRICE : 20
         * NET_CONTENT : 2.00
         * TASTE : 口味:鱼肉
         * STATUS : 3
         * BUY_NUM : 21
         */

        public int ORDER_DETAIL_ID;
        public int ORDER_ID;
        public int PRODUCT_ID;
        public int FORM_ID;
        public String LOGO;
        public String PROCUCT_NAME;
        public int KIND;
        public int NUM;
        public int REFUND_NUM;
        public int CURRENT_PRICE;
        public int PRICE;
        public String NET_CONTENT;
        public String TASTE;
        public int STATUS;
        public int BUY_NUM;

    }
}
