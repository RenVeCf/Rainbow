package com.ipd.taxiu.ui.fragment.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.ExchangeRecordAdapter;
import com.ipd.taxiu.adapter.MyIntegralAdapter;
import com.ipd.taxiu.bean.ExchangeRecordBean;
import com.ipd.taxiu.bean.MyIntegralBean;
import com.ipd.taxiu.ui.ListFragment;
import com.ipd.taxiu.ui.activity.coupon.IntegralExchangeActivity;
import com.ipd.taxiu.widget.MessageDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Miss on 2018/8/6
 */
public class MyIntegralFragment extends ListFragment<List<MyIntegralBean>,MyIntegralBean> implements View.OnClickListener {
    private MyIntegralAdapter mAdapter = null;
    private TextView btn_exchange;

    public static MyIntegralFragment newInstance() {
        MyIntegralFragment fragment = new MyIntegralFragment();
        return fragment;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        btn_exchange = getMRootView().findViewById(R.id.btn_exchange);
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
    public Observable<List<MyIntegralBean>> loadListData() {
        return Observable.create(new Observable.OnSubscribe<List<MyIntegralBean>>() {
            @Override
            public void call(Subscriber<? super List<MyIntegralBean>> subscriber) {
                List<MyIntegralBean> bean = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    bean.add(new MyIntegralBean());
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public int isNoMoreData(List<MyIntegralBean> result) {
        if (result == null || result.isEmpty()) {
            return getEMPTY_DATA();
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new MyIntegralAdapter(getData(),getContext());
            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, List<MyIntegralBean> result) {
        getData().addAll(result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exchange:
                Intent intent = new Intent(getContext(),IntegralExchangeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
