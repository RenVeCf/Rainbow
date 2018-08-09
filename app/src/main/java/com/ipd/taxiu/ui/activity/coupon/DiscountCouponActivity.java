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
import com.ipd.taxiu.ui.fragment.coupon.DiscountCouponFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/30
 * 我的优惠券
 */
public class DiscountCouponActivity extends BaseUIActivity {
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
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, DiscountCouponFragment.newInstance()).commit();
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

    @Override
    protected void initListener() {

    }
}
