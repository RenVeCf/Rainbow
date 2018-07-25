package com.ipd.taxiu.ui.activity.referral;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseActivity;
import com.ipd.taxiu.widget.MessageDialog;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/25
 * 查看他人主页
 */
public class HomepageActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_back;

    @Override
    protected int getBaseLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.activity_homepage;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        iv_back = findViewById(R.id.iv_back);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
