package com.ipd.taxiu.bean;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

public class PetInfoBean extends BaseIndexPinyinBean {
    public int res;
    public String name;

    public PetInfoBean(int res, String name) {
        this.res = res;
        this.name = name;
    }

    @Override
    public String getTarget() {
        return name;
    }
}
