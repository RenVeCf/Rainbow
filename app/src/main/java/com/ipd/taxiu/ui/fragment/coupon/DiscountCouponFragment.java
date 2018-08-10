package com.ipd.taxiu.ui.fragment.coupon;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.DiscountExchangeAdapter;
import com.ipd.taxiu.bean.CouponBean;
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
public class DiscountCouponFragment extends ListFragment<List<CouponBean>,CouponBean> {
    private DiscountExchangeAdapter mAdapter = null;

    public static DiscountCouponFragment newInstance() {
        DiscountCouponFragment fragment = new DiscountCouponFragment();
        return fragment;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        progress_layout.setEmptyViewRes(R.layout.layout_empty_coupon);
    }

    @NotNull
    @Override
    public Observable<List<CouponBean>> loadListData() {
        return Observable.create(new Observable.OnSubscribe<List<CouponBean>>() {
            @Override
            public void call(Subscriber<? super List<CouponBean>> subscriber) {
                List<CouponBean> bean = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    bean.add(new CouponBean());
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public int isNoMoreData(List<CouponBean> result) {
        if (result == null || result.isEmpty()) {
            return getEMPTY_DATA();
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new DiscountExchangeAdapter(getData(),getContext());
            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, List<CouponBean> result) {
        getData().addAll(result);
    }
}
