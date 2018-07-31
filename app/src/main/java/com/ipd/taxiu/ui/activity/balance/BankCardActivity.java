package com.ipd.taxiu.ui.activity.balance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.BankCardAdapter;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/30
 * 我的银行卡
 */
public class BankCardActivity extends BaseUIActivity implements View.OnClickListener{
    private List<String> data;
    private RecyclerView mRecyclerView;
    private BankCardAdapter mAdapter;
    private View view;
    private TextView btn_add_card;
    private String bankType;
    @Override
    protected int getContentLayout() {
        initData();
        bankType = getIntent().getStringExtra("bankType");
        return R.layout.layout_bank_card;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        mRecyclerView = findViewById(R.id.recycler_view);
        view = findViewById(R.id.layout_empty);
        btn_add_card = findViewById(R.id.btn_add_card);
        if (data.size() != 0) {
            view.setVisibility(View.GONE);
        }else {
            view.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }

    }

    @Override
    protected void loadData() {
        if (data.size() != 0) {
            mAdapter = new BankCardAdapter(data,this,bankType);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    protected void initListener() {
        btn_add_card.setOnClickListener(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "我的银行卡";
    }

    private void initData(){
        data = new ArrayList<>();
        for (int i = 0 ;i<4;i++){
            data.add("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_card:
                Intent intent = new Intent(this,AddBankCardActivity.class);
                startActivity(intent);
            break;
        }
    }
}
