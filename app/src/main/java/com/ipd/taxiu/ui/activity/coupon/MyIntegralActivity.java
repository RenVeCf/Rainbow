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
import com.ipd.taxiu.ui.fragment.coupon.MyIntegralFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/30
 * 我的积分
 */
public class MyIntegralActivity extends BaseUIActivity{

    private TextView btn_exchange;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_container;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        btn_exchange = findViewById(R.id.btn_exchange);

    }

    @Override
    protected void loadData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, MyIntegralFragment.newInstance()).commit();
    }

    @Override
    protected void initListener() {

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
}
