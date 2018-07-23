package com.ipd.taxiu.ui.activity.order;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.widget.AuditSuccessDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/23
 * 填写快递信息
 */
public class ExpressInfoActivity extends BaseUIActivity implements View.OnClickListener {
    private TextView tv_return_money_explanation;
    private Button btn_submit;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_express_info;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        tv_return_money_explanation = findViewById(R.id.tv_return_money_explanation);
        tv_return_money_explanation.setText("1、请先收到商品后，再进行申请退款操作，请勿拆开快递包裹；\n" +
                "2、您的退款申请提交后，待我们收到您寄回的商品后，我司会进行审核工作；\n" +
                "3、审核通过后，请将您收到的商品，按照快递单上的的地址寄回即可，寄回的快递费用由您承担，如是到付，我司一律不签收，一切后果自付；\n" +
                "4、我司收到您寄回的商品并确认无问题后，我们会在1~2个工作日内将订单实付金额退还到您的余额。");
        btn_submit = findViewById(R.id.btn_submit);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                initDialog();
                break;
        }
    }

    private void initDialog() {
        AuditSuccessDialog.Builder builder = new AuditSuccessDialog.Builder(this);
        builder.setTitle("您的快递信息已提交");
        builder.setMessage("等待我司收货并确认");
        builder.setCommit("我知道了", new AuditSuccessDialog.OnClickListener() {
            @Override
            public void onClick(AuditSuccessDialog.Builder builder) {
                builder.getDialog().dismiss();
            }
        });
        builder.getDialog().show();
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "填写快递信息";
    }
}
