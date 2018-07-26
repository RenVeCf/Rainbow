package com.ipd.taxiu.ui.activity.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.widget.MessageDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/24
 * 设置
 */
public class SettingActivity extends BaseUIActivity implements View.OnClickListener{
    private RelativeLayout rl_update_password,rl_about_us,rl_clear_cache,rl_common_problem;
    private TextView tv_service_phone;
    private String phoneNum;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        rl_update_password = findViewById(R.id.rl_update_password);
        rl_about_us = findViewById(R.id.rl_about_us);
        tv_service_phone = findViewById(R.id.tv_service_phone);
        phoneNum = tv_service_phone.getText().toString();
        rl_clear_cache = findViewById(R.id.rl_clear_cache);
        rl_common_problem = findViewById(R.id.rl_common_problem);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        rl_update_password.setOnClickListener(this);
        rl_about_us.setOnClickListener(this);
        tv_service_phone.setOnClickListener(this);
        rl_clear_cache.setOnClickListener(this);
        rl_common_problem.setOnClickListener(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "设置";
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.rl_update_password:
                intent = new Intent(this,UpdatePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_about_us:
                intent = new Intent(this,AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_service_phone:
                initDialog("确认要拨打客服电话吗？","他嗅宠物官方客服电话： "+phoneNum,"确认拨打","暂不拨打");
                break;
            case R.id.rl_clear_cache:
                toastShow("清除成功");
                break;
            case R.id.rl_common_problem:
                intent = new Intent(this,CommonProblemActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void initDialog(String title, String message, String commitStr, String cancelStr) {
        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCommit(commitStr, new MessageDialog.OnClickListener() {
            @Override
            public void onClick(MessageDialog.Builder builder) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + phoneNum);
                intent.setData(data);
                startActivity(intent);
                builder.getDialog().dismiss();
            }
        });
        builder.setCancel(cancelStr, new MessageDialog.OnClickListener() {
            @Override
            public void onClick(MessageDialog.Builder builder) {
                builder.getDialog().dismiss();
            }
        });
        builder.getDialog().show();
    }
}
