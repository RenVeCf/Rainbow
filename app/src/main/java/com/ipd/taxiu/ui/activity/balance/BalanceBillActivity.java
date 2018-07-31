package com.ipd.taxiu.ui.activity.balance;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.BalanceBillAdapter;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/30
 * 余额账单
 */
public class BalanceBillActivity extends BaseUIActivity{
    private RecyclerView recyclerView;
    private List<String> data;
    private BalanceBillAdapter mAdapter;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_balance_bill;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        recyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    protected void loadData() {
        initData();
        mAdapter = new BalanceBillAdapter(data,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "余额账单";
    }

    private void initData(){
        data = new ArrayList<>();
        for (int i = 0; i <5;i++){
            data.add("");
        }
    }
}
