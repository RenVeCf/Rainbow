package com.ipd.rainbow.ui.activity.referral;

import android.os.Bundle;

import com.ipd.rainbow.R;
import com.ipd.rainbow.ui.BaseUIActivity;
import com.ipd.rainbow.ui.fragment.referral.FriendListFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/25
 * 好友列表
 */
public class FriendListActivity extends BaseUIActivity {

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
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, FriendListFragment.newInstance()).commit();
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "好友列表";
    }
}
