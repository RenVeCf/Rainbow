package com.ipd.rainbow.bean;

import java.util.List;

/**
 * Created by Miss on 2018/9/7
 * "PET_ID": 1,
 * "USER_ID": 1,
 * "PET_TYPE_ID": 1,
 * "NICKNAME": "斯基大魔王",
 * "LOGO": "upload/default/dog.png",
 * "GENDER": 1,
 * "BIRTHDAY": "2017.01.01",
 * "SORT": 0,
 * "CATEGORY": 1,
 * "STATUS": 1,
 * "CREATETIME": "2018-08-14 18:11:41",
 * "MONTH_NUM": 20,
 * "DAY_NUM": 2,
 * "DAY_DATA": {
 * "DAY_ID": 3,
 * "CONTENT": "第二十个月2天",
 * "MONTH_NUM": 20,
 * "DAY_NUM": 2,
 * "CREATETIME": "2018-09-03 11:48:16",
 * "STATUS": 1
 * },
 * "CURREN_TTIME": "2018.09.03"
 */
public class ShowPetBean {
    public int PET_ID;
    public String USER_ID;
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
    public String CURREN_TTIME;
    public List<DayBean> DAY_LIST;
    public String SYSTEM_TTIME;
}
