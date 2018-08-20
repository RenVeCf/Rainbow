package com.ipd.taxiu.ui.activity.setting;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.CommonProblemAdapter;
import com.ipd.taxiu.bean.QuestionBean;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.ui.fragment.mine.setting.CommonProblemFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/24
 * 常见问题
 */
public class CommonProblemActivity extends BaseUIActivity {

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
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, CommonProblemFragment.Companion.newInstance()).commit();
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "常见问题";
    }

}
