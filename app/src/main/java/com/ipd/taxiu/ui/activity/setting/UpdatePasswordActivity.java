package com.ipd.taxiu.ui.activity.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/24
 * 修改登录密码
 */
public class UpdatePasswordActivity extends BaseUIActivity implements View.OnClickListener {
    private Button btn_submit;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_update_password;
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
        btn_submit.setOnClickListener(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "修改登录密码";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
               toastShow("修改成功");
            break;
        }
    }
}
