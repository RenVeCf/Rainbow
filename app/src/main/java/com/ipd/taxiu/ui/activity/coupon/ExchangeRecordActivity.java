package com.ipd.taxiu.ui.activity.coupon;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.ExchangeRecordAdapter;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/30
 * 兑换记录
 */
public class ExchangeRecordActivity extends BaseUIActivity {
    private RecyclerView recyclerView;
    private List<String> data;
    private ExchangeRecordAdapter mAdapter;

    @Override
    protected int getContentLayout() {
        initData();
        if (data.size() == 0){
            return R.layout.layout_empty_record;
        }else
        return R.layout.activity_exchange_record;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        recyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    protected void loadData() {
        if (data.size() != 0) {
            mAdapter = new ExchangeRecordAdapter(data, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(mAdapter);
        }

    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i<5;i++){
            data.add("");
        }
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "兑换记录";
    }
}
