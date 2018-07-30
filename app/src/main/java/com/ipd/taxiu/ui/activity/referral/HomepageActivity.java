package com.ipd.taxiu.ui.activity.referral;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.HomepageAdapter;
import com.ipd.taxiu.ui.BaseActivity;
import com.ipd.taxiu.widget.MessageDialog;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/25
 * 查看他人主页
 */
public class HomepageActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_back;
    private RecyclerView recycler_view;
    private List<String> data;
    private HomepageAdapter mAdapter;

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_homepage;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        iv_back = findViewById(R.id.iv_back);
        recycler_view = findViewById(R.id.recycler_view);
    }

    @Override
    protected void loadData() {
        initData();
        mAdapter = new HomepageAdapter(this,data);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(mAdapter);

    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void initData(){
        data = new ArrayList<>();
        for (int i= 0; i<2;i++){
            data.add("");
        }
    }
}
