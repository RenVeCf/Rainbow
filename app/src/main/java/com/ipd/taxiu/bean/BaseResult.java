package com.ipd.taxiu.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jumpbox on 16/8/23.
 */
public class BaseResult<T> {
    @SerializedName("response")
    public int code;
    public T data;
    @SerializedName("desc")
    public String msg;

    public BaseResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public BaseResult() {
    }
}
