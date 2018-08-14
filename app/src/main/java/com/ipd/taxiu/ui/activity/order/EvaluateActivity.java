package com.ipd.taxiu.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.EvaluateAdapter;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.widget.RatingBar;
import com.ipd.taxiu.widget.SpaceItemDecoration;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/21
 * 评价商品
 */
public class EvaluateActivity extends BaseUIActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private EvaluateAdapter mAdapter;
    private List<String> datas;
    private TextView button;



    @Override
    protected int getContentLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.activity_evaluate;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        mRecyclerView = findViewById(R.id.recycler_view_evaluate);
        button = findViewById(R.id.btn_evaluate);
    }

    @Override
    protected void loadData() {
        initData();
        mAdapter = new EvaluateAdapter(this, datas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(24));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addFooterView(LayoutInflater.from(this).inflate(R.layout.footer_evaluvate, null));
    }

    @Override
    protected void initListener() {
        button.setOnClickListener(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "发表评价";
    }

    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            datas.add(i + "");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_evaluate:
                toastShow("评价成功");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mAdapter.setReset(requestCode, resultCode, data,mAdapter.getSelectPosition());
    }
}
