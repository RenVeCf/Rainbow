package com.ipd.taxiu.ui.fragment.group;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.GroupListAdapter;
import com.ipd.taxiu.adapter.OrderListAdapter;
import com.ipd.taxiu.bean.GroupBean;
import com.ipd.taxiu.bean.GroupListBean;
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
 * 拼团
 */
public class GroupFragment extends ListFragment<GroupListBean, GroupBean> {
    private GroupListAdapter mAdapter = null;

    public static GroupFragment newInstance(int categoryId) {
        GroupFragment fragment = new GroupFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", categoryId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        progress_layout.setEmptyViewRes(R.layout.layout_empty_group);
    }

    @NotNull
    @Override
    public Observable<GroupListBean> loadListData() {
        final Bundle args = getArguments();
        final int categoryId = args.getInt("categoryId");
        return Observable.create(new Observable.OnSubscribe<GroupListBean>() {
            @Override
            public void call(Subscriber<? super GroupListBean> subscriber) {
                GroupListBean bean = new GroupListBean();
                bean.list = new ArrayList<>();
                if (categoryId == 0){
                    bean.list.add(new GroupBean(1));
                }else if (categoryId ==1){
                    bean.list.add(new GroupBean(2));
                }else {
                    bean.list.add(new GroupBean(3));
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public int isNoMoreData(GroupListBean result) {
        if (result.list == null || result.list.isEmpty()) {
            return getEMPTY_DATA();
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new GroupListAdapter(getContext(), getData());
            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, GroupListBean result) {
        getData().addAll(result.list);
    }
}
