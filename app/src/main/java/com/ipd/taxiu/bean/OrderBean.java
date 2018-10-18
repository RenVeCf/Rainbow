package com.ipd.taxiu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Miss on 2018/7/19
 */
public class OrderBean {
    public int ORDER_ID;
    public int USER_ID;
    public String ORDER_NO;
    public int PAYWAY;
    public String PAY_FEE;
    @SerializedName("STATUS")
    public int status;
    public List<ProductBean> PRODUCT_LIST;
}
