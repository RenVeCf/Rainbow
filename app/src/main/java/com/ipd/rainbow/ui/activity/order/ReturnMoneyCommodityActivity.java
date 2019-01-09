package com.ipd.rainbow.ui.activity.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.ipd.rainbow.R;
import com.ipd.rainbow.ui.BaseUIActivity;
import com.ipd.rainbow.ui.fragment.order.ReturnInfoFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/19
 * 退款退货
 */
public class ReturnMoneyCommodityActivity extends BaseUIActivity {
    private String[] titles = {"审核中","已通过","未通过"};
    private ViewPager viewPager;
    private SlidingTabLayout tab_layout;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_return_index;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        viewPager = findViewById(R.id.view_pager);
        tab_layout = findViewById(R.id.tab_layout);
    }

    @Override
    protected void loadData() {
        ReturnAdapter adapter = new ReturnAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tab_layout.setViewPager(viewPager, titles);
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "退款退货";
    }

    class ReturnAdapter extends FragmentPagerAdapter{

        public ReturnAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ReturnInfoFragment.Companion.newInstance(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
