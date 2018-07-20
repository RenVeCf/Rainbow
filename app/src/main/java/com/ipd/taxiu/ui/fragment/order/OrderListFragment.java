package com.ipd.taxiu.ui.fragment.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.OrderListAdapter;
import com.ipd.taxiu.bean.OrderBean;
import com.ipd.taxiu.bean.OrderListBean;
import com.ipd.taxiu.ui.ListFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Miss on 2018/7/19
 */
public class OrderListFragment extends ListFragment<OrderListBean, OrderBean> {
    private OrderListAdapter mAdapter = null;

    public static OrderListFragment newInstance(int categoryId) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", categoryId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        progress_layout.setEmptyViewRes(R.layout.layout_empty_order);
    }

    @NotNull
    @Override
    public Observable<OrderListBean> loadListData() {
        final Bundle args = getArguments();
        final int categoryId = args.getInt("categoryId");
        return Observable.create(new Observable.OnSubscribe<OrderListBean>() {
            @Override
            public void call(Subscriber<? super OrderListBean> subscriber) {
                OrderListBean bean = new OrderListBean();
                bean.list = new ArrayList<>();
                if (categoryId == 1) {
                    for (int i = 0; i < 1; i++) {
                        bean.list.add(new OrderBean(1));
                    }
                }
                else if (categoryId == 2) {
                    for (int i = 0; i < 2; i++) {
                        bean.list.add(new OrderBean(2));
                    }
                }
                else if (categoryId == 3) {
                    for (int i = 0; i < 3; i++) {
                        bean.list.add(new OrderBean(3));
                    }
                }
                else if (categoryId == 4) {
                    for (int i = 0; i < 4; i++) {
                        bean.list.add(new OrderBean(4));
                    }
                }else {
                    for (int i = 0; i < 4; i++) {
                        bean.list.add(new OrderBean(i+1));
                    }
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public int isNoMoreData(OrderListBean result) {
        if (result.list == null || result.list.isEmpty()) {
            return getEMPTY_DATA();
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new OrderListAdapter(getContext(), getData());
            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, OrderListBean result) {
        getData().addAll(result.list);
    }
}
