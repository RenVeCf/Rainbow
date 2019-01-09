package com.ipd.rainbow.ui.activity.setting;

import android.os.Bundle;

import com.ipd.rainbow.R;
import com.ipd.rainbow.ui.BaseUIActivity;
import com.ipd.rainbow.ui.fragment.mine.setting.CommonProblemFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/24
 * 常见问题
 */
public class CommonProblemActivity extends BaseUIActivity {

    @Override
    protected int getContentLayout() {
        return R.layout.activity_container;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
    }

    @Override
    protected void loadData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, CommonProblemFragment.Companion.newInstance()).commit();
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "常见问题";
    }

}
