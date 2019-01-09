package com.ipd.rainbow.bean;

import me.yokeyword.indexablerv.IndexableEntity;

public class ProductBrandBean implements IndexableEntity {

    public int BRAND_ID;
    public String BRAND_NAME;
    public String LOGO;
    public int IS_RECOMMEND;
    public int SORT;
    public String CREATETIME;
    public int STATUS;

    @Override
    public String getFieldIndexBy() {
        return BRAND_NAME;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.BRAND_NAME = indexField;

    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
    }
}
