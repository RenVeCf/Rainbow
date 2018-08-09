package com.ipd.taxiu.ui.activity.referral;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.FriendListAdapter;
import com.ipd.taxiu.bean.FriendBean;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.ui.fragment.referral.FriendListFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

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
