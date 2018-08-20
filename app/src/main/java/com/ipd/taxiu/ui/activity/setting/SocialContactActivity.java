package com.ipd.taxiu.ui.activity.setting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.AttentionBean;
import com.ipd.taxiu.presenter.MinePresenter;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.ui.fragment.collect.CollectClassRoomListFragment;
import com.ipd.taxiu.ui.fragment.collect.CollectStoreFragment;
import com.ipd.taxiu.ui.fragment.collect.CollectTalkListFragment;
import com.ipd.taxiu.ui.fragment.collect.CollectTaxiuListFragment;
import com.ipd.taxiu.ui.fragment.collect.CollectTopicListFragment;
import com.ipd.taxiu.ui.fragment.collect.SocicalContactFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/19
 * 我的社交
 */
public class SocialContactActivity extends BaseUIActivity {
    // TYPE 1关注 2粉丝
    private String[] titles = {"关注", "粉丝"};
    private ViewPager viewPager;
    private SlidingTabLayout tab_layout;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_contact_index;
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
        int contact = getIntent().getIntExtra("TYPE",0);
        if (contact == 2) {
            tab_layout.setCurrentTab(1);
        }else {
            tab_layout.setCurrentTab(0);
        }
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "我的社交";
    }

    class MyCollectAdapter extends FragmentPagerAdapter {

        public MyCollectAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SocicalContactFragment.newInstance(position+1);
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
