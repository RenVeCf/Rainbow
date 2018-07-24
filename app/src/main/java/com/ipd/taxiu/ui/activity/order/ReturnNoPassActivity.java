package com.ipd.taxiu.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/23
 * 退货退款详情【未通过】
 */
public class ReturnNoPassActivity extends BaseUIActivity implements View.OnClickListener {
    private int returnStatus, returnType;
    private ImageView goods_pic;
    private TextView delivery_time, tv_delivery_time, tv_order_status;

    @Override
    protected int getContentLayout() {
        returnStatus = getIntent().getIntExtra("returnStatus", 0);
        returnType = getIntent().getIntExtra("returnType", 0);
        return R.layout.activity_return_no_pass;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        goods_pic = findViewById(R.id.return_img);
        delivery_time = findViewById(R.id.delivery_time);
        tv_delivery_time = findViewById(R.id.tv_delivery_time);
        tv_order_status = findViewById(R.id.tv_order_status);

        if (returnType == 1) {
            goods_pic.setVisibility(View.GONE);
        } else {
            goods_pic.setVisibility(View.VISIBLE);
        }

        if (returnStatus == 2) {
            delivery_time.setVisibility(View.GONE);
            tv_delivery_time.setVisibility(View.GONE);
            tv_order_status.setText("待发货");
        } else if (returnStatus == 3) {
            delivery_time.setVisibility(View.VISIBLE);
            tv_delivery_time.setVisibility(View.VISIBLE);
            tv_order_status.setText("待收货");
            delivery_time.setText("发货时间");
        } else if (returnStatus == 4) {
            delivery_time.setVisibility(View.VISIBLE);
            tv_delivery_time.setVisibility(View.VISIBLE);
            tv_order_status.setText("已完成");
            delivery_time.setText("收货时间");
        }

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        if (returnType == 1) {
            return "退款详情";
        } else {
            return "退货详情";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
