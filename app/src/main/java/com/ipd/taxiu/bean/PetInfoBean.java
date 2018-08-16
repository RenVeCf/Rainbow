package com.ipd.taxiu.bean;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import me.yokeyword.indexablerv.IndexableEntity;

public class PetInfoBean implements IndexableEntity {
    /**
     * PET_TYPE_ID : 6
     * CATEGORY : 2
     * NAME : 澳大利亚梗
     * LOGO :
     * SORT : 6
     * CREATETIME : 2018-07-25 17:03:54
     * STATUS : 1
     */

    public int PET_TYPE_ID;
    public int CATEGORY;
    public String NAME;
    public String LOGO;
    public int SORT;
    public String CREATETIME;
    public int STATUS;

    @Override
    public String getFieldIndexBy() {
        return NAME;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.NAME = indexField;

    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
    }


}
