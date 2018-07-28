package com.ipd.taxiu.ui.activity.group;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.ui.fragment.collect.CollectClassRoomListFragment;
import com.ipd.taxiu.ui.fragment.collect.CollectStoreFragment;
import com.ipd.taxiu.ui.fragment.collect.CollectTalkListFragment;
import com.ipd.taxiu.ui.fragment.collect.CollectTaxiuListFragment;
import com.ipd.taxiu.ui.fragment.collect.CollectTopicListFragment;
import com.ipd.taxiu.ui.fragment.group.GroupFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/19
 * 我的拼团
 */
public class GroupBookingActivity extends BaseUIActivity {
    private String[] titles = {"待成团","已成团","未成团"};
    private ViewPager viewPager;
    private SlidingTabLayout tab_layout;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_group_index;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        viewPager = findViewById(R.id.view_pager);
        tab_layout = findViewById(R.id.tab_layout);
    }

    @Override
    protected void loadData() {
        GroupBookingAdapter adapter = new GroupBookingAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tab_layout.setViewPager(viewPager, titles);
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "我的拼团";
    }

    class GroupBookingAdapter extends FragmentPagerAdapter{

        public GroupBookingAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new GroupFragment().newInstance(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
