package com.ipd.taxiu.ui.activity.setting;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private TextView tips;
    private EditText et_original_password, et_new_password, et_affirm_new_password;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_update_password;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        btn_submit = findViewById(R.id.btn_submit);
        tips = findViewById(R.id.tips);
        et_original_password = findViewById(R.id.et_original_password);
        et_new_password = findViewById(R.id.et_new_password);
        et_affirm_new_password = findViewById(R.id.et_affirm_new_password);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        btn_submit.setOnClickListener(this);
        et_original_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    tips.setVisibility(View.GONE);
                }
            }
        });
        et_new_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    tips.setVisibility(View.GONE);
                }
            }
        });
        et_affirm_new_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    tips.setVisibility(View.GONE);
                }
            }
        });
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "修改登录密码";
    }

    @Override
    public void onClick(View v) {
        String originalPassword = et_original_password.getText().toString();
        String newPassword = et_new_password.getText().toString();
        String affirmPassword = et_affirm_new_password.getText().toString();
        switch (v.getId()) {
            case R.id.btn_submit:
                if (originalPassword.equals("")){
                    toastShow("原密码不能为空");
                    return;
                }
                if (newPassword.equals("")){
                    toastShow("请输入新密码");
                    return;
                }
                if (affirmPassword.equals("")){
                    toastShow("请输入再次确认密码");
                    return;
                }
                if (!originalPassword.equals("123456")) {
                    tips.setVisibility(View.VISIBLE);
                    tips.setText("原密码不对");
                    return;
                }
                if (!newPassword.equals(affirmPassword)) {
                    tips.setVisibility(View.VISIBLE);
                    tips.setText("两次密码不一致");
                }else {
                    tips.setVisibility(View.GONE);
                    toastShow("修改成功");
                    finish();
                }
                break;
        }
    }
}
