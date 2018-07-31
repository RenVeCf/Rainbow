package com.ipd.taxiu.ui.activity.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.IntegralExchangeAdapter;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/30
 * 积分兑换
 */
public class IntegralExchangeActivity extends BaseUIActivity {
    private RecyclerView recycler_view;
    private IntegralExchangeAdapter mAdapter;
    private List<String> data;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_integral_exchange;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        recycler_view = findViewById(R.id.recycler_view);
    }

    @Override
    protected void loadData() {
        initData();
        mAdapter = new IntegralExchangeAdapter(data,this);
        recycler_view.setLayoutManager(new GridLayoutManager(this,2));
        recycler_view.setAdapter(mAdapter);
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0 ;i<20;i++){
            data.add("");
        }
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "积分兑换";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_exchange_record,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.integral_record){
            Intent intent = new Intent(this,ExchangeRecordActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}
