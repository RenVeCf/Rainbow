package com.ipd.rainbow.ui.activity.setting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.ipd.rainbow.R;
import com.ipd.rainbow.ui.BaseUIActivity;
import com.ipd.rainbow.ui.fragment.classroom.CollectClassroomFragment;
import com.ipd.rainbow.ui.fragment.classroom.CollectStoreFragment;
import com.ipd.rainbow.ui.fragment.classroom.CollectTalkFragment;
import com.ipd.rainbow.ui.fragment.classroom.CollectTaxiuFragment;
import com.ipd.rainbow.ui.fragment.classroom.CollectTopicFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/19
 * 我的收藏
 */
public class MyCollectActivity extends BaseUIActivity {
    private String[] titles = {"商品", "它秀", "课堂", "话题", "问答"};
    private ViewPager viewPager;
    private SlidingTabLayout tab_layout;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_collect_index;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        viewPager = findViewById(R.id.view_pager);
        tab_layout = findViewById(R.id.tab_layout);
    }

    @Override
    protected void loadData() {
        MyCollectAdapter adapter = new MyCollectAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tab_layout.setViewPager(viewPager, titles);
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "我的收藏";
    }

    class MyCollectAdapter extends FragmentPagerAdapter {

        public MyCollectAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return CollectStoreFragment.Companion.newInstance();
            } else if (position == 1) {
                return CollectTaxiuFragment.Companion.newInstance();
            } else if (position == 2) {
                return CollectClassroomFragment.Companion.newInstance();
            } else if (position == 3) {
                return CollectTopicFragment.Companion.newInstance();
            } else {
                return CollectTalkFragment.Companion.newInstance();
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
