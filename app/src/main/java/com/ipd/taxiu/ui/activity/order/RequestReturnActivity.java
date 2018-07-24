package com.ipd.taxiu.ui.activity.order;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.widget.AuditSuccessDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/22
 * 申请退货
 */
public class RequestReturnActivity extends BaseUIActivity {
    private Button btn_submit;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_request_return;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        btn_submit = findViewById(R.id.btn_submit);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog();
            }
        });

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "申请退货";
    }

    private void initDialog(){
        AuditSuccessDialog.Builder builder = new AuditSuccessDialog.Builder(this);
        builder.setTitle("申请已提交，审核中");
        builder.setMessage("审核状态在 我的-退货记录 中查看");
        builder.setCommit("我知道了",new AuditSuccessDialog.OnClickListener() {
            @Override
            public void onClick(AuditSuccessDialog.Builder builder) {
                builder.getDialog().dismiss();
            }
        });
        builder.getDialog().show();

    }
}
