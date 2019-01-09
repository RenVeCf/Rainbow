package com.ipd.rainbow.bean;

import android.text.TextUtils;

/**
 * Created by Miss on 2018/8/17
 * "USER_ID": 1,
 * "USERNAME": "",
 * "PHONE": "15921895749",
 * "NICKNAME": "主人_TXCW2954",
 * "PASSWORD": "08fced95a72372c38bec237434fc9dd3",
 * "PASSWORDSALT": "0ff69938640841ce8e7871c2737578eb",
 * "LOGO": "/upload/default/logo.png",
 * "GENDER": 0,
 * "BIRTHDAY": "",
 * "STEP": 0,
 * "PET_TIME": "",
 * "TAG": "",
 * "MY_CODE": "woqrnd",
 * "TWO_CODE": "/upload/default/two_code.png",
 * "RECOMMEND_CODE": "",
 * "FANS_NUM": 0,
 * "ATTENTION_NUM": 0,
 * "WECHAT": "111111",
 * "QQ": "",
 * "ALIPAY": "",
 * "STATUS": 1,
 * "SORT": 1,
 * "CREATETIME": "2018-07-31 16:54:21"
 */
public class UserBean {
    public int USER_ID;
    public String USERNAME;
    public String PHONE;
    public String NICKNAME;
    public String PASSWORD;
    public String PASSWORDSALT;
    public String LOGO;
    public int GENDER;
    public String BIRTHDAY;
    public int STEP;
    public String PET_TIME;
    private String TAG;
    public String MY_CODE;
    public String TWO_CODE;
    public String RECOMMEND_CODE;
    public int FANS_NUM;
    public int ATTENTION_NUM;
    public String WECHAT;
    public String QQ;
    public String ALIPAY;
    public int STATUS;
    public int SORT;
    public int COLLECT_NUM;
    public int IS_ATTEN;
    public int IS_SELF;
    public String CREATETIME;

    public String getTag() {
        if (TextUtils.isEmpty(TAG)) {
            return "这个人很懒,什么都没留下";
        } else {
            return TAG;
        }
    }
}
