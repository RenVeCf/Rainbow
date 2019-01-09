package com.ipd.rainbow.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipd.rainbow.R;
import com.ipd.rainbow.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/23
 * 退货记录详情【已通过】
 */
public class ReturnRecordDetailActivity extends BaseUIActivity implements View.OnClickListener {
    private int returnStatus, returnType;
    private Button btn_express_information;
    private ImageView goods_pic;
    private TextView delivery_time, tv_delivery_time, tv_order_status;
    private RelativeLayout back_express_message;

    @Override
    protected int getContentLayout() {
        returnStatus = getIntent().getIntExtra("returnStatus", 0);
        returnType = getIntent().getIntExtra("returnType", 0);
        return R.layout.activity_return_record_detail;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        btn_express_information = findViewById(R.id.btn_express_information);
//        goods_pic = findViewById(R.id.return_img);
        delivery_time = findViewById(R.id.delivery_time);
        tv_delivery_time = findViewById(R.id.tv_delivery_time);
        tv_order_status = findViewById(R.id.tv_order_status);
        back_express_message = findViewById(R.id.rl_back_express_message);

        if (returnType == 1) {
            goods_pic.setVisibility(View.GONE);
            back_express_message.setVisibility(View.GONE);
        } else {
            goods_pic.setVisibility(View.VISIBLE);
            back_express_message.setVisibility(View.VISIBLE);
        }

        if (returnStatus == 2) {
            delivery_time.setVisibility(View.GONE);
            tv_delivery_time.setVisibility(View.GONE);
            btn_express_information.setVisibility(View.GONE);
            tv_order_status.setText("待发货");
        } else if (returnStatus == 3) {
            delivery_time.setVisibility(View.VISIBLE);
            tv_delivery_time.setVisibility(View.VISIBLE);
            btn_express_information.setVisibility(View.VISIBLE);
            tv_order_status.setText("待收货");
            delivery_time.setText("发货时间");
        } else if (returnStatus == 4) {
            delivery_time.setVisibility(View.VISIBLE);
            tv_delivery_time.setVisibility(View.VISIBLE);
            btn_express_information.setVisibility(View.VISIBLE);
            tv_order_status.setText("已完成");
            delivery_time.setText("收货时间");
        }

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        btn_express_information.setOnClickListener(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        if (returnType == 1) {
            return "退款记录详情";
        } else {
            return "退货记录详情";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_express_information:
                Intent intent = new Intent(this, ExpressInfoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
