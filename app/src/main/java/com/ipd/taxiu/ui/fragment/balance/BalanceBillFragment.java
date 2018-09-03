package com.ipd.taxiu.ui.fragment.balance;

import android.support.v7.widget.LinearLayoutManager;

import com.ipd.taxiu.adapter.BalanceBillAdapter;
import com.ipd.taxiu.bean.BalanceBillBean;
import com.ipd.taxiu.ui.ListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Miss on 2018/8/6
 */
public class BalanceBillFragment extends ListFragment<List<BalanceBillBean>,BalanceBillBean> {
    private BalanceBillAdapter mAdapter = null;

    public static BalanceBillFragment newInstance() {
        BalanceBillFragment fragment = new BalanceBillFragment();
        return fragment;
    }
    @NotNull
    @Override
    public Observable<List<BalanceBillBean>> loadListData() {
        return Observable.create(new Observable.OnSubscribe<List<BalanceBillBean>>() {
            @Override
            public void call(Subscriber<? super List<BalanceBillBean>> subscriber) {
                List<BalanceBillBean> bean = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    bean.add(new BalanceBillBean());
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public int isNoMoreData(List<BalanceBillBean> result) {
        if (result == null || result.isEmpty()) {
            return getEMPTY_DATA();
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new BalanceBillAdapter(getContext(),getData());
            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, List<BalanceBillBean> result) {
        getData().addAll(result);
    }
}
