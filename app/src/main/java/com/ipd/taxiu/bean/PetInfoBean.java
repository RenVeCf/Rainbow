package com.ipd.taxiu.bean;

import me.yokeyword.indexablerv.IndexableEntity;

public class PetInfoBean implements IndexableEntity {
    public int res;
    public String name;

    public PetInfoBean(int res, String name) {
        this.res = res;
        this.name = name;
    }

    @Override
    public String getFieldIndexBy() {
        return name;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.name = indexField;

    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
    }
}
