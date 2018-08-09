package com.ipd.taxiu.ui.activity.address;

import android.os.Bundle;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.ui.fragment.address.DeliveryAddressFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/25
 * 收货地址
 */
public class DeliveryAddressActivity extends BaseUIActivity {

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
       getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, DeliveryAddressFragment.newInstance()).commit();

    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "收货地址";
    }

}
