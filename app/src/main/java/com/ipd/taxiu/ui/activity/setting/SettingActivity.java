package com.ipd.taxiu.ui.activity.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils;
import com.ipd.taxiu.R;
import com.ipd.taxiu.platform.global.GlobalParam;
import com.ipd.taxiu.platform.http.HttpUrl;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.ui.activity.web.WebActivity;
import com.ipd.taxiu.utils.CleanMessageUtil;
import com.ipd.taxiu.widget.MessageDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/24
 * 设置
 */
public class SettingActivity extends BaseUIActivity implements View.OnClickListener{
    private RelativeLayout rl_update_password,rl_about_us,rl_clear_cache,rl_common_problem;
    private TextView tv_service_phone,exit_account,tv_cache;
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
        exit_account = findViewById(R.id.exit_account);
        tv_cache = findViewById(R.id.tv_cache);
        try {
            tv_cache.setText(CleanMessageUtil.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        exit_account.setOnClickListener(this);
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
                WebActivity.launch(getMActivity(), WebActivity.URL, HttpUrl.WEB_URL + HttpUrl.ABOUT_US, "关于我们");
//                intent = new Intent(this,AboutUsActivity.class);
//                startActivity(intent);
                break;
            case R.id.tv_service_phone:
                initDialog("确认要拨打客服电话吗？","他嗅宠物官方客服电话： "+phoneNum,"确认拨打","暂不拨打");
                break;
            case R.id.rl_clear_cache:
                clearCacheDialog();
                break;
            case R.id.rl_common_problem:
                intent = new Intent(this,CommonProblemActivity.class);
                startActivity(intent);
                break;
            case R.id.exit_account:
                initExitDialog();
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
                CommonUtils.callPhone(SettingActivity.this,phoneNum);
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

    private void clearCacheDialog() {
        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("你确定要清除缓存吗？");
        builder.setCommit("确定", new MessageDialog.OnClickListener() {
            @Override
            public void onClick(MessageDialog.Builder builder) {
                CleanMessageUtil.clearAllCache(getApplicationContext());
                try {
                    tv_cache.setText(CleanMessageUtil.getTotalCacheSize(getApplicationContext()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                builder.getDialog().dismiss();
            }
        });
        builder.setCancel("取消", new MessageDialog.OnClickListener() {
            @Override
            public void onClick(MessageDialog.Builder builder) {
                builder.getDialog().dismiss();
            }
        });
        builder.getDialog().show();
    }

    private void initExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要退出该登录账户吗？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                GlobalParam.onExitUser();
                GlobalParam.isLoginOrJump();
            }
        });
        builder.show();
    }
}
