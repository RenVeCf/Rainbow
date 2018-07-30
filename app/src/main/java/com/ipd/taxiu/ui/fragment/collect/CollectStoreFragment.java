package com.ipd.taxiu.ui.fragment.collect;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.CollectStoreAdapter;
import com.ipd.taxiu.bean.CollectStoreBean;
import com.ipd.taxiu.bean.CollectStoreListBean;
import com.ipd.taxiu.ui.ListFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Miss on 2018/7/19
 */
public class CollectStoreFragment extends ListFragment<CollectStoreListBean, CollectStoreBean> {
    private CollectStoreAdapter mAdapter = null;

    public static CollectStoreFragment newInstance() {
        CollectStoreFragment fragment = new CollectStoreFragment();
        return fragment;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        progress_layout.setEmptyViewRes(R.layout.layout_empty_collect);
    }

    @NotNull
    @Override
    public Observable<CollectStoreListBean> loadListData() {
        return Observable.create(new Observable.OnSubscribe<CollectStoreListBean>() {
            @Override
            public void call(Subscriber<? super CollectStoreListBean> subscriber) {
                CollectStoreListBean bean = new CollectStoreListBean();
                bean.list = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    bean.list.add(new CollectStoreBean());
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public int isNoMoreData(CollectStoreListBean result) {
        if (result.list == null || result.list.isEmpty()) {
            return getEMPTY_DATA();
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new CollectStoreAdapter(getContext(), getData());
            recycler_view.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, CollectStoreListBean result) {
        getData().addAll(result.list);
    }
}
