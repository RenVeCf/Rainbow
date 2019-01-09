package com.ipd.rainbow.ui.activity.referral;

import android.os.Bundle;

import com.ipd.rainbow.R;
import com.ipd.rainbow.ui.BaseUIActivity;
import com.ipd.rainbow.ui.fragment.referral.RecommendEarningsFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/25
 * 推荐收益
 */
public class RecommendEarningsActivity extends BaseUIActivity{
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
       getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, RecommendEarningsFragment.Companion.newInstance()).commit();
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "推荐收益";
    }

}
