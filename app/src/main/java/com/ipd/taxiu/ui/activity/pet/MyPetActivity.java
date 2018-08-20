package com.ipd.taxiu.ui.activity.pet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.MyPetAdapter;
import com.ipd.taxiu.bean.PetBean;
import com.ipd.taxiu.presenter.PetPresenter;
import com.ipd.taxiu.ui.BaseActivity;
import com.ipd.taxiu.ui.fragment.pet.MyPetFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

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
