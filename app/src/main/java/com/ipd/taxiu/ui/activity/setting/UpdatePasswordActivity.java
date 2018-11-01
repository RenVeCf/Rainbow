package com.ipd.taxiu.ui.activity.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils;
import com.ipd.taxiu.R;
import com.ipd.taxiu.presenter.MinePresenter;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/24
 * 修改登录密码
 */
public class UpdatePasswordActivity extends BaseUIActivity implements View.OnClickListener, MinePresenter.IUpdatePwdView {
    private TextView btn_submit;
    private TextView tips;
    private EditText et_original_password, et_new_password, et_affirm_new_password;

    private MinePresenter mPresenter;

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
    protected void onViewAttach() {
        super.onViewAttach();
        mPresenter = new MinePresenter();
        mPresenter.attachView(this, this);
    }

    @Override
    protected void onViewDetach() {
        super.onViewDetach();
        mPresenter.detachView();
        mPresenter = null;
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
        String originalPassword = et_original_password.getText().toString();
        String newPassword = et_new_password.getText().toString();
        String affirmPassword = et_affirm_new_password.getText().toString();
        switch (v.getId()) {
            case R.id.btn_submit:
                if (originalPassword.equals("")) {
                    tips.setVisibility(View.VISIBLE);
                    tips.setText("原密码不能为空");
                    return;
                }
                if (!CommonUtils.passwordIsLegal(newPassword)) {
                    tips.setVisibility(View.VISIBLE);
                    tips.setText("请输入新登录密码(数字+字母组合)");
                    return;
                }
                if (!CommonUtils.passwordIsLegal(affirmPassword)) {
                    tips.setVisibility(View.VISIBLE);
                    tips.setText("请再次输入新登录密码(数字+字母组合)");
                    return;
                }
                if (!newPassword.equals(affirmPassword)) {
                    tips.setVisibility(View.VISIBLE);
                    tips.setText("两次密码不一致");
                    return;
                }
                mPresenter.updatePwd(newPassword, originalPassword);
                break;

        }
    }

    @Override
    public void updateSuccess() {
        tips.setVisibility(View.GONE);
        toastShow(true, "修改成功");
        finish();
    }

    @Override
    public void updateFail(@NotNull String errMsg) {
        tips.setVisibility(View.VISIBLE);
        tips.setText(errMsg);
    }
}
