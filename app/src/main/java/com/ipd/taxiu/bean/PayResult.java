package com.ipd.taxiu.bean;

import com.google.gson.annotations.SerializedName;

public class PayResult<T> extends BaseResult<Integer> {
    @SerializedName("data2")
    public T info;
}
