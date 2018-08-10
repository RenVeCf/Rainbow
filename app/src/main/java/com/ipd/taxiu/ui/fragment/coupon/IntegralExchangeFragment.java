package com.ipd.taxiu.ui.fragment.coupon;

import android.support.v7.widget.GridLayoutManager;
import com.ipd.taxiu.adapter.IntegralExchangeAdapter;
import com.ipd.taxiu.bean.ExchangeBean;
import com.ipd.taxiu.ui.ListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Miss on 2018/8/6
 */
public class IntegralExchangeFragment extends ListFragment<List<ExchangeBean>,ExchangeBean> {
    private IntegralExchangeAdapter mAdapter = null;

    public static IntegralExchangeFragment newInstance() {
        IntegralExchangeFragment fragment = new IntegralExchangeFragment();
        return fragment;
    }
    @NotNull
    @Override
    public Observable<List<ExchangeBean>> loadListData() {
        return Observable.create(new Observable.OnSubscribe<List<ExchangeBean>>() {
            @Override
            public void call(Subscriber<? super List<ExchangeBean>> subscriber) {
                List<ExchangeBean> bean = new ArrayList<>();
                bean = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    bean.add(new ExchangeBean());
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public int isNoMoreData(List<ExchangeBean> result) {
        if (result == null || result.isEmpty()) {
            return getEMPTY_DATA();
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new IntegralExchangeAdapter( getData(),getContext());
            recycler_view.setLayoutManager(new GridLayoutManager(getContext(),2));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, List<ExchangeBean> result) {
        getData().addAll(result);
    }
}
