package com.ipd.taxiu.ui.fragment.referral;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.FriendListAdapter;
import com.ipd.taxiu.bean.FriendBean;
import com.ipd.taxiu.bean.FriendListBean;
import com.ipd.taxiu.ui.ListFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Miss on 2018/8/6
 */
public class FriendListFragment extends ListFragment<FriendListBean,FriendBean> {
    private FriendListAdapter mAdapter = null;

    public static FriendListFragment newInstance() {
        FriendListFragment fragment = new FriendListFragment();
        return fragment;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        progress_layout.setEmptyViewRes(R.layout.layout_empty_friends);
    }
    @NotNull
    @Override
    public Observable<FriendListBean> loadListData() {
        return Observable.create(new Observable.OnSubscribe<FriendListBean>() {
            @Override
            public void call(Subscriber<? super FriendListBean> subscriber) {
                FriendListBean bean = new FriendListBean();
                bean.list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    bean.list.add(new FriendBean());
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public int isNoMoreData(FriendListBean result) {
        if (result.list == null || result.list.isEmpty()) {
            return getEMPTY_DATA();
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new FriendListAdapter(getContext(), getData());
            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, FriendListBean result) {
        getData().addAll(result.list);
    }
}
