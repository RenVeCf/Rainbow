package com.ipd.taxiu.ui.activity.balance;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.BalanceBillAdapter;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.ui.fragment.balance.BalanceBillFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/30
 * 余额账单
 */
public class BalanceBillActivity extends BaseUIActivity{
    @Override
    protected int getContentLayout() {
        return R.layout.activity_container;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
    }

    @Override
    protected void loadData() {
     getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, BalanceBillFragment.newInstance()).commit();
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "余额账单";
    }
}
