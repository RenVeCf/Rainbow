package com.ipd.taxiu.ui.activity.coupon;

import android.os.Bundle;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/30
 * 积分规则
 */
public class IntegralRuleActivity extends BaseUIActivity {
    @Override
    protected int getContentLayout() {
        return R.layout.activity_integral_rule;
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
        return "积分规则";
    }
}
