package com.ipd.taxiu.ui.activity.setting;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.CommonProblemAdapter;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/24
 * 常见问题
 */
public class CommonProblemActivity extends BaseUIActivity {
    private RecyclerView recyclerView;
    private CommonProblemAdapter adapter;
    private List<String> datas;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_common_problem;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        recyclerView = findViewById(R.id.recycler_view_common_problem);
    }

    @Override
    protected void loadData() {
        initData();
        adapter = new CommonProblemAdapter(this, datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "常见问题";
    }

    private void initData(){
        datas = new ArrayList<>();
        datas.add("如何注册成为平台用户？");
        datas.add("拼团失败后，已支付的款项退回到哪里？");
        datas.add("拼团成功后，可以取消拼团订单吗？");
        datas.add("正在拼团中的订单，可以取消吗？");
        datas.add("如何获得商品优惠券？");
        datas.add("已签收的商品有质量问题，如何退换？");
        datas.add("退货后，已支付的款项会退回吗？");
        datas.add("我的余额提现为什么总是很久才会到账？");
    }
}
