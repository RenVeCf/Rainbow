package com.ipd.taxiu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PetKindListBean {

    /**
     * ALEPH : A
     * List : [{"PET_TYPE_ID":6,"CATEGORY":2,"NAME":"澳大利亚梗","LOGO":"","SORT":6,"CREATETIME":"2018-07-25 17:03:54","STATUS":1},{"PET_TYPE_ID":7,"CATEGORY":2,"NAME":"爱尔兰雪达犬","LOGO":"","SORT":7,"CREATETIME":"2018-08-15 16:34:20","STATUS":1}]
     */

    public String ALEPH;
    @SerializedName("List")
    public List<PetInfoBean> list;


}
