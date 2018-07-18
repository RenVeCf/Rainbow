package com.ipd.taxiu.bean;

/**
 * Created by jumpbox on 16/8/23.
 */
public class BaseResult<T> {
    public int code;
    public T data;
    public String msg;

    public BaseResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public BaseResult() {
    }
}
