package com.ipd.taxiu.ui.fragment.referral;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.EarningDetailAdapter;
import com.ipd.taxiu.bean.RecommendEarningsBean;
import com.ipd.taxiu.ui.ListFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Miss on 2018/8/6
 */
public class RecommendEarningsFragment extends ListFragment<List<RecommendEarningsBean>,RecommendEarningsBean> {
    private EarningDetailAdapter mAdapter = null;

    public static RecommendEarningsFragment newInstance() {
        RecommendEarningsFragment fragment = new RecommendEarningsFragment();
        return fragment;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        progress_layout.setEmptyViewRes(R.layout.layout_empty_earning);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_recommend_earning;
    }

    @NotNull
    @Override
    public Observable<List<RecommendEarningsBean>> loadListData() {
        return Observable.create(new Observable.OnSubscribe<List<RecommendEarningsBean>>() {
            @Override
            public void call(Subscriber<? super List<RecommendEarningsBean>> subscriber) {
                List<RecommendEarningsBean> bean = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    bean.add(new RecommendEarningsBean());
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public int isNoMoreData(List<RecommendEarningsBean> result) {
        if (result == null || result.isEmpty()) {
            return getEMPTY_DATA();
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new EarningDetailAdapter(getContext(),getData());
            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, List<RecommendEarningsBean> result) {
        getData().addAll(result);
    }
}
