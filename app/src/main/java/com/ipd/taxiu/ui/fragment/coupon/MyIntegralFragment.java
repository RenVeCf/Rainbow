package com.ipd.taxiu.ui.fragment.coupon;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.MyIntegralAdapter;
import com.ipd.taxiu.bean.BaseResult;
import com.ipd.taxiu.bean.IntegralBean;
import com.ipd.taxiu.platform.global.GlobalParam;
import com.ipd.taxiu.platform.http.ApiManager;
import com.ipd.taxiu.ui.ListFragment;
import com.ipd.taxiu.ui.activity.coupon.IntegralExchangeActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Miss on 2018/8/6
 */
public class MyIntegralFragment extends ListFragment<List<IntegralBean>, IntegralBean> implements View.OnClickListener {
    private MyIntegralAdapter mAdapter = null;
    private TextView btn_exchange, tv_account_integral_num;
    private String score = "0";

    public static MyIntegralFragment newInstance() {
        MyIntegralFragment fragment = new MyIntegralFragment();
        return fragment;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
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
    public Observable<List<IntegralBean>> loadListData() {
        return ApiManager.getService().scoreList(10,GlobalParam.getUserId(), getPage())
                .map(new Func1<BaseResult<List<IntegralBean>>, List<IntegralBean>>() {

                    @Override
                    public List<IntegralBean> call(BaseResult<List<IntegralBean>> result) {
                        List<IntegralBean> bean = new ArrayList<>();
                        if (result.code == 0) {
                            bean .addAll(result.data);
                        }
                        score = result.score+"";
                        return bean;
                    }
                });
    }

    @Override
    public int isNoMoreData(List<IntegralBean> result) {
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
    public void addData(boolean isRefresh, List<IntegralBean> result) {
        tv_account_integral_num.setText(score);
        getData().addAll(result);
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
