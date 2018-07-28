package com.ipd.taxiu.ui.fragment.collect;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.CollectStoreAdapter;
import com.ipd.taxiu.adapter.ContactAdapter;
import com.ipd.taxiu.bean.CollectStoreBean;
import com.ipd.taxiu.bean.CollectStoreListBean;
import com.ipd.taxiu.bean.ContactBean;
import com.ipd.taxiu.bean.ContactListBean;
import com.ipd.taxiu.ui.ListFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Miss on 2018/7/19
 * 社交
 */
public class SocicalContactFragment extends ListFragment<ContactListBean, ContactBean> {
    private ContactAdapter mAdapter = null;

    public static SocicalContactFragment newInstance(int categoryId) {
        SocicalContactFragment fragment = new SocicalContactFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", categoryId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        progress_layout.setEmptyViewRes(R.layout.layout_empty_contact);
    }

    @NotNull
    @Override
    public Observable<ContactListBean> loadListData() {
        Bundle bundle = getArguments();
        final int categoryId = bundle.getInt("categoryId");
        return Observable.create(new Observable.OnSubscribe<ContactListBean>() {
            @Override
            public void call(Subscriber<? super ContactListBean> subscriber) {
                ContactListBean bean = new ContactListBean();
                bean.list = new ArrayList<>();
                if (categoryId == 0) {
                    for (int i = 0; i < 10; i++) {
                        bean.list.add(new ContactBean(1));
                    }
                }else {
                    for (int i = 0; i < 10; i++) {
                        if (i==2) {
                            bean.list.add(new ContactBean(3));
                        }else {
                            bean.list.add(new ContactBean(2));
                        }
                    }
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public int isNoMoreData(ContactListBean result) {
        if (result.list == null || result.list.isEmpty()) {
            return getEMPTY_DATA();
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new ContactAdapter(getContext(), getData());
            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, ContactListBean result) {
        getData().addAll(result.list);
    }
}
