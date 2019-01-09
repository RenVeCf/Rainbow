package com.ipd.rainbow.ui.fragment.coupon;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.ipd.rainbow.R;
import com.ipd.rainbow.adapter.MyIntegralAdapter;
import com.ipd.rainbow.bean.BaseResult;
import com.ipd.rainbow.bean.IntegralBean;
import com.ipd.rainbow.platform.global.GlobalParam;
import com.ipd.rainbow.platform.http.ApiManager;
import com.ipd.rainbow.ui.ListFragment;
import com.ipd.rainbow.ui.activity.coupon.IntegralExchangeActivity;

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
        if (result == null || result.isEmpty()){
            return getNO_MORE_DATA();
        }
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
