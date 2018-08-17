package com.ipd.taxiu.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * Created by Miss on 2018/8/15
 * "REGION_ID": "110000",
 * "AREA_NAME": "北京",
 * "PARENT_ID": "0",
 * "SHORT_NAME": "北京",
 * "SORT": 1,
 * "RegionList":
 * 省
 */
public class ProvinceBean implements IPickerViewData {
    private String REGION_ID;
    private String AREA_NAME;
    private String PARENT_ID;
    private String SHORT_NAME;
    private int SORT;
    private List<CityBean> RegionList;

    public String getREGION_ID() {
        return REGION_ID;
    }

    public void setREGION_ID(String REGION_ID) {
        this.REGION_ID = REGION_ID;
    }

    public String getAREA_NAME() {
        return AREA_NAME;
    }

    public void setAREA_NAME(String AREA_NAME) {
        this.AREA_NAME = AREA_NAME;
    }

    public String getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(String PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }

    public String getSHORT_NAME() {
        return SHORT_NAME;
    }

    public void setSHORT_NAME(String SHORT_NAME) {
        this.SHORT_NAME = SHORT_NAME;
    }

    public int getSORT() {
        return SORT;
    }

    public void setSORT(int SORT) {
        this.SORT = SORT;
    }

    public List<CityBean> getRegionList() {
        return RegionList;
    }

    public void setRegionList(List<CityBean> regionList) {
        RegionList = regionList;
    }

    @Override
    public String getPickerViewText() {
        return this.getAREA_NAME();
    }
}
