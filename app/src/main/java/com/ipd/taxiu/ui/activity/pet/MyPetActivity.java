package com.ipd.taxiu.ui.activity.pet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.MyPetAdapter;
import com.ipd.taxiu.ui.BaseActivity;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/26
 * 我的宠物
 */
public class MyPetActivity extends BaseActivity implements View.OnClickListener{
    private RecyclerView recycler_view;
    private List<String> data = new ArrayList<>();
    private MyPetAdapter mAdapter;

    private ImageView iv_back;
    private TextView tv_add_pet,empty_add;

    @Override
    protected int getBaseLayout() {
        initData();
        if (data.size() == 0) {
            return R.layout.layout_empty_pet;
        } else
            return R.layout.activity_my_pet;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        recycler_view = findViewById(R.id.recycler_view);
        iv_back = findViewById(R.id.iv_back);
        tv_add_pet = findViewById(R.id.tv_add_pet);

        if (data.size() != 0) {
        }else {
            empty_add = findViewById(R.id.empty_add);
        }
    }

    @Override
    protected void loadData() {
        if (data.size() != 0) {
            mAdapter = new MyPetAdapter(this, data);
            recycler_view.setLayoutManager(new LinearLayoutManager(this));
            recycler_view.setAdapter(mAdapter);
        }

    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        tv_add_pet.setOnClickListener(this);
        if (data.size() != 0){

        }else {
            empty_add.setOnClickListener(this);
        }

    }

    private void initData() {
        for (int i = 0; i < 2; i++) {
            data.add("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add_pet:
                startActivity();
                break;
            case R.id.empty_add:
                startActivity();
                break;
        }
    }

    public void startActivity() {
        Intent intent = new Intent(this,AddPetActivity.class);
        intent.putExtra("petWay","AddPet");
        startActivity(intent);
    }
}
