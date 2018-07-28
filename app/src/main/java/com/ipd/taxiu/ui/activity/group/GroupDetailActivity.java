package com.ipd.taxiu.ui.activity.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.GroupDetailAdapter;
import com.ipd.taxiu.adapter.OrderDetailAdapter;
import com.ipd.taxiu.bean.OrderDetailBean;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.ui.activity.order.EvaluateActivity;
import com.ipd.taxiu.ui.activity.order.RequestReturnActivity;
import com.ipd.taxiu.ui.activity.order.RequestReturnMoneyActivity;
import com.ipd.taxiu.widget.MessageDialog;
import com.ipd.taxiu.widget.PayWayDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static com.ipd.taxiu.adapter.OrderListAdapter.DELIVERY;
import static com.ipd.taxiu.adapter.OrderListAdapter.DETAIL;
import static com.ipd.taxiu.adapter.OrderListAdapter.EVALUATE;
import static com.ipd.taxiu.adapter.OrderListAdapter.PAYMENT;

/**
 * Created by Miss on 2018/7/20
 * 拼团详情
 */
public class GroupDetailActivity extends BaseUIActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private GroupDetailAdapter adapter;
    private ArrayList<String> datas;
    private int  GroupStatus;
    private LinearLayout ll_button;


    @Override
    protected int getContentLayout() {
        GroupStatus = getIntent().getIntExtra("GroupStatus",1);
        return R.layout.activity_group_detail;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        recyclerView = findViewById(R.id.recycler_view_order_detail);
        ll_button = findViewById(R.id.ll_button);

        if (GroupStatus == 3){
            ll_button.setVisibility(View.VISIBLE);
            ll_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initMessageDialog();
                }
            });
        }

    }

    @Override
    protected void loadData() {
        initData();
        adapter = new GroupDetailAdapter(datas,this,GroupStatus);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.addFooterView(LayoutInflater.from(this).inflate(R.layout.item_order_detail_footer, null));
        adapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_group_detail_header, null));
    }

    @Override
    protected void initListener() {
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "拼团详情";
    }

    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            datas.add("");
        }
    }


    private void initMessageDialog() {
        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setTitle("确认要删除该拼团吗");
        builder.setMessage("拼团信息删除后不可撤销，请谨慎操作。");
        builder.setCommit("确认删除", new MessageDialog.OnClickListener() {
            @Override
            public void onClick(MessageDialog.Builder builder) {
                toastShow("删除成功");
                builder.getDialog().dismiss();
            }
        });
        builder.setCancel("暂不删除", new MessageDialog.OnClickListener() {
            @Override
            public void onClick(MessageDialog.Builder builder) {
                builder.getDialog().dismiss();
            }
        });
        builder.getDialog().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

}

