package com.ipd.taxiu.ui.activity.setting;

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
import com.ipd.taxiu.ui.fragment.taxiu.TaxiuListFragment;
import com.ipd.taxiu.ui.fragment.topic.TopicListFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/19
 * 我的收藏
 */
public class MyCollectActivity extends BaseUIActivity {
    private String[] titles = {"商品","它秀","课堂","话题","问答"};
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

    class MyCollectAdapter extends FragmentPagerAdapter{

        public MyCollectAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                return CollectStoreFragment.newInstance();
            }else if (position == 1) {
                return new CollectTaxiuListFragment();
            }else if (position == 2){
                return new CollectClassRoomListFragment();
            }else if (position == 3){
                return new CollectTopicListFragment();
            }else {
                return new CollectTalkListFragment();
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
