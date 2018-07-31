package com.ipd.taxiu.ui.activity.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.DiscountExchangeAdapter;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/30
 * 我的优惠券
 */
public class DiscountCouponActivity extends BaseUIActivity {
    private List<String> data;
    private RecyclerView recyclerView;
    private DiscountExchangeAdapter mAdapter;

    @Override
    protected int getContentLayout() {
        initData();
        if (data.size() == 0) {
            return R.layout.layout_empty_coupon;
        } else
            return R.layout.activity_discount_coupon;
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("");
        }
    }


    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        recyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    protected void loadData() {
        if (data.size() != 0){
            mAdapter = new DiscountExchangeAdapter(data, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "我的优惠券";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_integral_exhcange, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.integral_exchange) {
            Intent intent = new Intent(this, IntegralExchangeActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}
