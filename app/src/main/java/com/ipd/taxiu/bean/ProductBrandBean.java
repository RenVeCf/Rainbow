package com.ipd.taxiu.bean;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

public class ProductBrandBean extends BaseIndexPinyinBean {
    public String name;

    public ProductBrandBean(String name) {
        this.name = name;
    }

    @Override
    public String getTarget() {
        return name;
    }
}
