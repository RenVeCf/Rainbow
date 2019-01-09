package com.ipd.rainbow.ui.activity.pet;

import android.os.Bundle;

import com.ipd.rainbow.R;
import com.ipd.rainbow.ui.BaseActivity;
import com.ipd.rainbow.ui.fragment.pet.MyPetFragment;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/26
 * 我的宠物
 */
public class MyPetActivity extends BaseActivity{

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_container;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected void loadData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, MyPetFragment.Companion.newInstance()).commit();
    }

    @Override
    protected void initListener() {

    }

}
