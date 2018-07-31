package com.ipd.taxiu.ui.activity.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.MyIntegralAdapter;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/30
 * 我的积分
 */
public class MyIntegralActivity extends BaseUIActivity implements View.OnClickListener{
    private RecyclerView recycler_view;
    private MyIntegralAdapter mAdapter;
    private List<String> datas;

    private TextView btn_exchange;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_my_integral;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        recycler_view = findViewById(R.id.recycler_view);
        btn_exchange = findViewById(R.id.btn_exchange);

    }

    @Override
    protected void loadData() {
        initData();
        mAdapter = new MyIntegralAdapter(datas,this);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        btn_exchange.setOnClickListener(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "我的积分";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_integral_rule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.integral_rule) {
            Intent intent = new Intent(this,IntegralRuleActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datas.add("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exchange:
                Intent intent = new Intent(this,IntegralExchangeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
