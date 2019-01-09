package com.ipd.rainbow.bean;

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

    /**
     * REGION_ID : 110000
     * AREA_NAME : 北京
     * PARENT_ID : 0
     * SHORT_NAME : 北京
     * SORT : 1
     * RegionList : [{"REGION_ID":"110100","AREA_NAME":"北京市","PARENT_ID":"110000","SHORT_NAME":"北京","SORT":1,"RegionList":[{"REGION_ID":"110101","AREA_NAME":"东城区","PARENT_ID":"110100","SHORT_NAME":"东城","SORT":3,"RegionList":[]},{"REGION_ID":"110102","AREA_NAME":"西城区","PARENT_ID":"110100","SHORT_NAME":"西城","SORT":15,"RegionList":[]},{"REGION_ID":"110105","AREA_NAME":"朝阳区","PARENT_ID":"110100","SHORT_NAME":"朝阳","SORT":2,"RegionList":[]},{"REGION_ID":"110106","AREA_NAME":"丰台区","PARENT_ID":"110100","SHORT_NAME":"丰台","SORT":6,"RegionList":[]},{"REGION_ID":"110107","AREA_NAME":"石景山区","PARENT_ID":"110100","SHORT_NAME":"石景山","SORT":12,"RegionList":[]},{"REGION_ID":"110108","AREA_NAME":"海淀区","PARENT_ID":"110100","SHORT_NAME":"海淀","SORT":7,"RegionList":[]},{"REGION_ID":"110109","AREA_NAME":"门头沟区","PARENT_ID":"110100","SHORT_NAME":"门头沟","SORT":9,"RegionList":[]},{"REGION_ID":"110111","AREA_NAME":"房山区","PARENT_ID":"110100","SHORT_NAME":"房山","SORT":5,"RegionList":[]},{"REGION_ID":"110112","AREA_NAME":"通州区","PARENT_ID":"110100","SHORT_NAME":"通州","SORT":14,"RegionList":[]},{"REGION_ID":"110113","AREA_NAME":"顺义区","PARENT_ID":"110100","SHORT_NAME":"顺义","SORT":13,"RegionList":[]},{"REGION_ID":"110114","AREA_NAME":"昌平区","PARENT_ID":"110100","SHORT_NAME":"昌平","SORT":1,"RegionList":[]},{"REGION_ID":"110115","AREA_NAME":"大兴区","PARENT_ID":"110100","SHORT_NAME":"大兴","SORT":4,"RegionList":[]},{"REGION_ID":"110116","AREA_NAME":"怀柔区","PARENT_ID":"110100","SHORT_NAME":"怀柔","SORT":8,"RegionList":[]},{"REGION_ID":"110117","AREA_NAME":"平谷区","PARENT_ID":"110100","SHORT_NAME":"平谷","SORT":11,"RegionList":[]},{"REGION_ID":"110228","AREA_NAME":"密云县","PARENT_ID":"110100","SHORT_NAME":"密云","SORT":10,"RegionList":[]},{"REGION_ID":"110229","AREA_NAME":"延庆县","PARENT_ID":"110100","SHORT_NAME":"延庆","SORT":16,"RegionList":[]}]}]
     */

    public String REGION_ID;
    public String AREA_NAME;
    public String PARENT_ID;
    public String SHORT_NAME;
    public int SORT;
    public List<ProvinceBean> RegionList;

    @Override
    public String getPickerViewText() {
        return AREA_NAME;
    }
}
