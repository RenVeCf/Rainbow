package com.ipd.taxiu.ui.activity.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.DeliveryAddressAdapter;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/25
 * 收货地址
 */
public class DeliveryAddressActivity extends BaseUIActivity implements View.OnClickListener{
    private List<String> data;
    private RecyclerView recycler_view;
    private DeliveryAddressAdapter mAdapter;
    private TextView btn_add_address;

    @Override
    protected int getContentLayout() {
        initData();
        if (data.size() == 0) {
            return R.layout.layout_empty_address;
        } else
            return R.layout.activity_delivery_address;
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
        recycler_view = findViewById(R.id.recycler_view);
        btn_add_address = findViewById(R.id.btn_add_address);
    }

    @Override
    protected void loadData() {
        if (data.size() != 0){
            mAdapter = new DeliveryAddressAdapter(this,data);
            recycler_view.setLayoutManager(new LinearLayoutManager(this));
            recycler_view.setAdapter(mAdapter);
        }

    }

    @Override
    protected void initListener() {
        btn_add_address.setOnClickListener(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "收货地址";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_address:
                Intent intent = new Intent(this,AddAddressActivity.class);
                intent.putExtra("addressType","添加地址");
                startActivity(intent);
                break;
        }
    }
}
