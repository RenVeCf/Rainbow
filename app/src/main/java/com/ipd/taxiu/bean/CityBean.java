package com.ipd.taxiu.bean;

import java.util.List;

/**
 * Created by Miss on 2018/8/15
 * <p>
 * "REGION_ID": "110100",
 * "AREA_NAME": "北京市",
 * "PARENT_ID": "110000",
 * "SHORT_NAME": "北京",
 * "SORT": 1,
 * "RegionList"
 * 市
 */
public class CityBean {
    public String REGION_ID;
    public String AREA_NAME;
    public String PARENT_ID;
    public String SHORT_NAME;
    public int SORT;
    public List<AreaBean> RegionList;

}
