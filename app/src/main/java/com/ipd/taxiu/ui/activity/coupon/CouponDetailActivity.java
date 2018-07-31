package com.ipd.taxiu.ui.activity.coupon;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.widget.MessageDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/30
 * 优惠券详情
 */
public class CouponDetailActivity extends BaseUIActivity implements View.OnClickListener{
    private TextView btn_exchange;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_coupon_detail;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        btn_exchange = findViewById(R.id.btn_exchange);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        btn_exchange.setOnClickListener(this);

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "优惠券详情";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exchange:
                initMessageDialog();
                break;
        }
    }

    private void initMessageDialog() {
        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setTitle("确认要兑换该优惠券吗？");
        builder.setMessage("兑换后发放至我的-优惠券列表中");
        builder.setCommit("确认兑换", new MessageDialog.OnClickListener() {
            @Override
            public void onClick(MessageDialog.Builder builder) {
                toastShow(true,"兑换成功");
                builder.getDialog().dismiss();
            }
        });
        builder.setCancel("暂不兑换", new MessageDialog.OnClickListener() {
            @Override
            public void onClick(MessageDialog.Builder builder) {
                builder.getDialog().dismiss();
            }
        });
        builder.getDialog().show();
    }
}
