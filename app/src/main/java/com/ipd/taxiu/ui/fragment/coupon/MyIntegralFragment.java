package com.ipd.taxiu.ui.fragment.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.ExchangeRecordAdapter;
import com.ipd.taxiu.adapter.MyIntegralAdapter;
import com.ipd.taxiu.bean.BaseResult;
import com.ipd.taxiu.bean.ExchangeRecordBean;
import com.ipd.taxiu.bean.IntegralBean;
import com.ipd.taxiu.bean.IntegralListBean;
import com.ipd.taxiu.platform.global.GlobalParam;
import com.ipd.taxiu.platform.http.ApiManager;
import com.ipd.taxiu.ui.ListFragment;
import com.ipd.taxiu.ui.activity.coupon.IntegralExchangeActivity;
import com.ipd.taxiu.widget.MessageDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Miss on 2018/8/6
 */
public class MyIntegralFragment extends ListFragment<IntegralListBean, IntegralBean> implements View.OnClickListener {
    private MyIntegralAdapter mAdapter = null;
    private TextView btn_exchange, tv_account_integral_num;

    public static MyIntegralFragment newInstance() {
        MyIntegralFragment fragment = new MyIntegralFragment();
        return fragment;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        getProgress_layout().setEmptyViewRes(R.layout.activity_empty_integral);
        btn_exchange = getMRootView().findViewById(R.id.btn_exchange);
        tv_account_integral_num = getMRootView().findViewById(R.id.tv_account_integral_num);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_my_integral;
    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_exchange.setOnClickListener(this);
    }

    @NotNull
    @Override
    public Observable<IntegralListBean> loadListData() {
        return ApiManager.getService().scoreList(10,GlobalParam.getUserId(), getPage())
                .map(new Func1<BaseResult<IntegralListBean>, IntegralListBean>() {

                    @Override
                    public IntegralListBean call(BaseResult<IntegralListBean> result) {
                        IntegralListBean bean = new IntegralListBean();
                        if (result.code == 0) {
                            bean = result.data;
                        }
                        return bean;
                    }
                });
    }

    @Override
    public int isNoMoreData(IntegralListBean result) {
        return getNORMAL();
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new MyIntegralAdapter(getData(), getContext());
            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, IntegralListBean result) {
        getData().addAll(result.data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exchange:
                IntegralExchangeActivity.Companion.launch(getMActivity());
                break;
        }
    }
}
