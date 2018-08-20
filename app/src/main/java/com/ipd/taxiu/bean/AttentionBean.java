package com.ipd.taxiu.bean;

import java.io.Serializable;

/**
 * Created by Miss on 2018/8/18
 * <p>
 * "MY_USER_ID": 1,
 * "USER_ID": 2,
 * "CREATETIME": "2018-08-14 14:07:16",
 * "NICKNAME": "QQ",
 * "LOGO": "",
 * "TAG": "标签2",
 * "IS_ATTEN": 2  关注状态 0未关注  1已关注  2相互关注
 */
public class AttentionBean implements Serializable{
    public int MY_USER_ID;
    public int USER_ID;
    public String CREATETIME;
    public String NICKNAME;
    public String LOGO;
    public String TAG;
    public int IS_ATTEN;
}
