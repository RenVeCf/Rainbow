package com.ipd.taxiu.ui.activity.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.OrderDetailAdapter;
import com.ipd.taxiu.bean.OrderDetailBean;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.widget.PayWayDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Created by Miss on 2018/7/20
 */
public class OrderDetailActivity extends BaseUIActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private OrderDetailAdapter adapter;
    private ArrayList<OrderDetailBean> datas;
    private PayWayDialog dialog;
    private TextView tv_pay;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        recyclerView = findViewById(R.id.recycler_view_order_detail);
        tv_pay = findViewById(R.id.tv_order_payment);

    }

    @Override
    protected void loadData() {
        initData();
        adapter = new OrderDetailAdapter(datas, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.addFooterView(LayoutInflater.from(this).inflate(R.layout.item_order_detail_footer, null));
        adapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_order_detail_header, null));
    }

    @Override
    protected void initListener() {
        tv_pay.setOnClickListener(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "订单详情";
    }

    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            datas.add(new OrderDetailBean());
        }
    }

    private void initDialog() {
        dialog = new PayWayDialog(this, R.style.recharge_pay_dialog);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_order_payment:
                initDialog();
                break;
        }
    }
}
