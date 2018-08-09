package com.ipd.taxiu.ui.fragment.coupon;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.ExchangeRecordAdapter;
import com.ipd.taxiu.bean.ExchangeRecordBean;
import com.ipd.taxiu.bean.FriendBean;
import com.ipd.taxiu.bean.FriendListBean;
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
public class ExchangeRecordFragment extends ListFragment<List<ExchangeRecordBean>,ExchangeRecordBean> {
    private ExchangeRecordAdapter mAdapter = null;

    public static ExchangeRecordFragment newInstance() {
        ExchangeRecordFragment fragment = new ExchangeRecordFragment();
        return fragment;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        progress_layout.setEmptyViewRes(R.layout.layout_empty_record);
    }

    @NotNull
    @Override
    public Observable<List<ExchangeRecordBean>> loadListData() {
        return Observable.create(new Observable.OnSubscribe<List<ExchangeRecordBean>>() {
            @Override
            public void call(Subscriber<? super List<ExchangeRecordBean>> subscriber) {
                List<ExchangeRecordBean> bean = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    bean.add(new ExchangeRecordBean());
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public int isNoMoreData(List<ExchangeRecordBean> result) {
        if (result == null || result.isEmpty()) {
            return getEMPTY_DATA();
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new ExchangeRecordAdapter(getData(),getContext());
            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, List<ExchangeRecordBean> result) {
        getData().addAll(result);
    }
}
