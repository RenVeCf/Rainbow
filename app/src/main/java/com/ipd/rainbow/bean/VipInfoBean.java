package com.ipd.rainbow.bean;

import java.util.List;

public class VipInfoBean {


    /**
     * USER_ID : 11
     * NICKNAME : 哈哈哈
     * LOGO : upload/user/20190220/aff8a5723f4f4b278e42a66eed8367d7.png
     * GENDER : 0
     * MY_CODE : coabji
     * TWO_CODE : upload/ZXCode//coabji.png
     * LEVEL : 0
     * END_TIME : 
     * STATUS : 1
     * CREATETIME : 2019-02-19 11:23:02
     * AUTO_PAY : 0
     * LEVEL_NAME : 白金会员
     * LEVEL_CONTENT : <p>白金会员专属特权说明</p>
     * VIP_EXCLUSIVE : <p>白金会员专属特权说明</p>
     * VIP_DESC : <p>白金会员资费说明</p>
     * VIP_LIST : [{"LEVEL":1,"LEVEL_NAME":"白金会员","VIP_EXCLUSIVE":"<p>白金会员专属特权说明<\/p>","VIP_DESC":"<p>白金会员资费说明<\/p>","LIST":[{"TYPE":1,"LEVEL":1,"TYPE_NAME":"包月","CURRENT_PRICE":58,"PRICE":40.03,"AUTO_PAY":1,"CONTENT":"开通自动续费"},{"TYPE":2,"LEVEL":1,"TYPE_NAME":"包季","CURRENT_PRICE":156.7,"PRICE":174,"AUTO_PAY":0,"CONTENT":"可享受9.0折优惠"},{"TYPE":3,"LEVEL":1,"TYPE_NAME":"包年","CURRENT_PRICE":556.9,"PRICE":696,"AUTO_PAY":0,"CONTENT":"可享受8.0折优惠"}]},{"LEVEL":2,"LEVEL_NAME":"铂金会员","VIP_EXCLUSIVE":"<p>铂金会员专属特权说明<\/p>","VIP_DESC":"<p>铂金会员资费说明<\/p>","LIST":[{"TYPE":1,"LEVEL":2,"TYPE_NAME":"包月","CURRENT_PRICE":108,"PRICE":100.45,"AUTO_PAY":1,"CONTENT":"开通自动续费"},{"TYPE":2,"LEVEL":2,"TYPE_NAME":"包季","CURRENT_PRICE":291.7,"PRICE":324,"AUTO_PAY":0,"CONTENT":"可享受9.0折优惠"},{"TYPE":3,"LEVEL":2,"TYPE_NAME":"包年","CURRENT_PRICE":1036.8,"PRICE":1296,"AUTO_PAY":0,"CONTENT":"可享受8.0折优惠"}]},{"LEVEL":3,"LEVEL_NAME":"钻石会员","VIP_EXCLUSIVE":"<p>钻石会员资费说明钻石会员资费说明钻石会员资费说明钻石会员资费说明钻石会员资费说明<\/p>","VIP_DESC":"<p>钻石会员专属特权说明钻石会员专属特权说明钻石会员专属特权说明钻石会员专属特权说明<\/p>","LIST":[]}]
     */

    public int USER_ID;
    public String NICKNAME;
    public String LOGO;
    public int GENDER;
    public String MY_CODE;
    public String TWO_CODE;
    public int LEVEL;
    public String END_TIME;
    public int STATUS;
    public String CREATETIME;
    public int AUTO_PAY;
    public String LEVEL_NAME;
    public String LEVEL_CONTENT;
    public String VIP_EXCLUSIVE;
    public String VIP_DESC;
    public List<VipLevelBean> VIP_LIST;


    public static class VipLevelBean {
        /**
         * LEVEL : 1
         * LEVEL_NAME : 白金会员
         * VIP_EXCLUSIVE : <p>白金会员专属特权说明</p>
         * VIP_DESC : <p>白金会员资费说明</p>
         * LIST : [{"TYPE":1,"LEVEL":1,"TYPE_NAME":"包月","CURRENT_PRICE":58,"PRICE":40.03,"AUTO_PAY":1,"CONTENT":"开通自动续费"},{"TYPE":2,"LEVEL":1,"TYPE_NAME":"包季","CURRENT_PRICE":156.7,"PRICE":174,"AUTO_PAY":0,"CONTENT":"可享受9.0折优惠"},{"TYPE":3,"LEVEL":1,"TYPE_NAME":"包年","CURRENT_PRICE":556.9,"PRICE":696,"AUTO_PAY":0,"CONTENT":"可享受8.0折优惠"}]
         */

        public int LEVEL;
        public String LEVEL_NAME;
        public String VIP_EXCLUSIVE;
        public String VIP_DESC;
        public List<VipPriceBean> LIST;
        public List<VipPriceBean> AUTO_PAY_LIST;


        public static class VipPriceBean {
            /**
             * TYPE : 1
             * LEVEL : 1
             * TYPE_NAME : 包月
             * CURRENT_PRICE : 58.0
             * PRICE : 40.03
             * AUTO_PAY : 1
             * CONTENT : 开通自动续费
             */

            public int TYPE;
            public int LEVEL;
            public String TYPE_NAME;
            public String CURRENT_PRICE;
            public String PRICE;
            public int AUTO_PAY;
            public String CONTENT;

        }
    }
}
