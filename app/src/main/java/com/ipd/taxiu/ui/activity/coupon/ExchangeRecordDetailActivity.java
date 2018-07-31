package com.ipd.taxiu.ui.activity.coupon;

import android.os.Bundle;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/30
 * 兑换记录详情
 */
public class ExchangeRecordDetailActivity extends BaseUIActivity{
    @Override
    protected int getContentLayout() {
        return R.layout.activity_exchange_record_detail;
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
        return "兑换记录详情";
    }
}
