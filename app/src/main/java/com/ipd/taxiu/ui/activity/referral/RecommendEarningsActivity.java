package com.ipd.taxiu.ui.activity.referral;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.EarningDetailAdapter;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/25
 * 推荐收益
 */
public class RecommendEarningsActivity extends BaseUIActivity implements View.OnClickListener{
    private RecyclerView recycler_view_earning_detail;
    private EarningDetailAdapter mAdapter;
    private List<String> data;
    private TextView tv_earning_number;
    @Override
    protected int getContentLayout() {
        initData();
        if (data.size() == 0) {
            return R.layout.layout_empty_earning;
        }else
        return R.layout.activity_recommend_earning;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        recycler_view_earning_detail = findViewById(R.id.recycler_view_earning_detail);
        tv_earning_number = findViewById(R.id.tv_earning_number);
    }

    @Override
    protected void loadData() {
        if (data.size() == 0) {
            tv_earning_number.setText("0");
        }else {
            tv_earning_number.setText("22222");
            mAdapter = new EarningDetailAdapter(this,data);
            recycler_view_earning_detail.setLayoutManager(new LinearLayoutManager(this));
            recycler_view_earning_detail.setAdapter(mAdapter);
        }
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0 ; i< 10 ; i++){
            data.add("");
        }
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "推荐收益";
    }

    @Override
    public void onClick(View v) {

    }
}
