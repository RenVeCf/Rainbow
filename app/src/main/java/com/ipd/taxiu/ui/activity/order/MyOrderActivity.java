package com.ipd.taxiu.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.ui.fragment.order.OrderListFragment;
import com.ipd.taxiu.ui.fragment.topic.TopicListFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Created by Miss on 2018/7/19
 * 订单信息
 */
public class MyOrderActivity extends BaseUIActivity {
    private String[] titles = {"全部","待付款","待发货","待收货","已完成"};
    private ViewPager viewPager;
    private SlidingTabLayout tab_layout;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_order_index;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        viewPager = findViewById(R.id.view_pager);
        tab_layout = findViewById(R.id.tab_layout);
    }

    @Override
    protected void loadData() {
        OrderAdapter adapter = new OrderAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tab_layout.setViewPager(viewPager, titles);
        Intent intent = getIntent();
        int status = intent.getIntExtra("status",0);
        switch (status){
            case 1:
                tab_layout.setCurrentTab(1);
                break;
            case 2:
                tab_layout.setCurrentTab(2);
                break;
            case 3:
                tab_layout.setCurrentTab(3);
                break;
            case 4:
                tab_layout.setCurrentTab(4);
                break;
           default:
               tab_layout.setCurrentTab(0);
               break;
        }
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "我的订单";
    }

    class OrderAdapter extends FragmentPagerAdapter{

        public OrderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return OrderListFragment.Companion.newInstance(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
