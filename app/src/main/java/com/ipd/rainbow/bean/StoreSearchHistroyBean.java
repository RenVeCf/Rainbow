package com.ipd.rainbow.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreSearchHistroyBean extends BaseResult<List<SearchKeyBean>> {
    @SerializedName("data2")
    public List<SearchKeyBean> historyList;
}
