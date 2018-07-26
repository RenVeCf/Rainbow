package com.ipd.taxiu.ui.activity.referral;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.FriendListAdapter;
import com.ipd.taxiu.bean.FriendBean;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/25
 * 好友列表
 */
public class FriendListActivity extends BaseUIActivity {
    private RecyclerView recycler_view_friend_list;
    private List<FriendBean> data;
    private FriendListAdapter mAdapter;

    @Override
    protected int getContentLayout() {
        initData();
        if (data.size() == 0){
            return R.layout.layout_empty_friends;
        }else {
            return R.layout.activity_friend_list;
        }
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        recycler_view_friend_list = findViewById(R.id.recycler_view_friend_list);
    }

    @Override
    protected void loadData() {
        if (data.size() != 0) {
            mAdapter = new FriendListAdapter(this, data);
            recycler_view_friend_list.setLayoutManager(new LinearLayoutManager(this));
            recycler_view_friend_list.setAdapter(mAdapter);
        }
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "好友列表";
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new FriendBean());
        }
    }
}
