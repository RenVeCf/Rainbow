package com.ipd.rainbow.ui.activity.order;

import android.os.Bundle;

import com.ipd.rainbow.R;
import com.ipd.rainbow.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Created by Miss on 2018/7/21
 * 物流详情
 */
public class LogisticsDetailActivity extends BaseUIActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_logistics;
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
        return "订单物流详情";
    }
}
