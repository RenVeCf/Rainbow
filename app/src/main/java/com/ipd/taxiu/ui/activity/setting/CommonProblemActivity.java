package com.ipd.taxiu.ui.activity.setting;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.CommonProblemAdapter;
import com.ipd.taxiu.bean.QuestionListBean;
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
    private List<QuestionListBean> datas;

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
        datas.add(new QuestionListBean("如何注册成为平台用户？","注册内容注册内容注册内容注册内容"));
        datas.add(new QuestionListBean("拼团失败后，已支付的款项退回到哪里？","拼团失败内容拼团失败内容"));
        datas.add(new QuestionListBean("拼团成功后，可以取消拼团订单吗？","拼团成功内容拼团成功内容"));
        datas.add(new QuestionListBean("正在拼团中的订单，可以取消吗？","正在拼团中的订单内容"));
        datas.add(new QuestionListBean("如何获得商品优惠券？","如何获得商品优惠券内容"));
        datas.add(new QuestionListBean("已签收的商品有质量问题，如何退换？","已签收的商品有质量内容"));
        datas.add(new QuestionListBean("退货后，已支付的款项会退回吗？","退货内容退货内容退货内容"));
        datas.add(new QuestionListBean("我的余额提现为什么总是很久才会到账？","余额提现内容余额提现内容余额提现内容"));
    }
}
