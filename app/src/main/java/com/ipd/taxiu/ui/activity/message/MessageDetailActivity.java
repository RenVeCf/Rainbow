package com.ipd.taxiu.ui.activity.message;

import android.os.Bundle;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/31
 * 详情
 */
public class MessageDetailActivity extends BaseUIActivity{
    @Override
    protected int getContentLayout() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "详情";
    }
}
