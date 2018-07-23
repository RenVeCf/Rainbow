package com.ipd.taxiu.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/23
 * 退货记录详情【已通过】
 */
public class ReturnRecordDetailActivity extends BaseUIActivity implements View.OnClickListener{
//    private int returnStatus, returnType;
    private Button btn_express_information;

    @Override
    protected int getContentLayout() {
//        returnStatus = getIntent().getIntExtra("returnStatus", 0);
//        returnType = getIntent().getIntExtra("returnType", 0);
        return R.layout.activity_return_record_detail;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        btn_express_information = findViewById(R.id.btn_express_information);

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
        return "退货记录详情";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_express_information:
                Intent intent = new Intent(this,ExpressInfoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
