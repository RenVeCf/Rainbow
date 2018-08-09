package com.ipd.taxiu.ui.activity.balance;

import android.os.Bundle;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.ui.fragment.balance.BankCardFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Created by Miss on 2018/7/30
 * 我的银行卡
 */
public class BankCardActivity extends BaseUIActivity{
    private String bankType;
    @Override
    protected int getContentLayout() {
        bankType = getIntent().getStringExtra("bankType");
        return R.layout.activity_container;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
    }

    @Override
    protected void loadData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, BankCardFragment.newInstance(bankType)).commit();
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "我的银行卡";
    }

}
