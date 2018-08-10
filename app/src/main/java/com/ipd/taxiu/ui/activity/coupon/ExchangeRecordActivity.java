package com.ipd.taxiu.ui.activity.coupon;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.ExchangeRecordAdapter;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.ui.fragment.coupon.ExchangeRecordFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/30
 * 兑换记录
 */
public class ExchangeRecordActivity extends BaseUIActivity {

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
       getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, ExchangeRecordFragment.newInstance()).commit();
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
