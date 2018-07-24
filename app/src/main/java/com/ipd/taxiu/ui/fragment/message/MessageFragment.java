package com.ipd.taxiu.ui.fragment.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.OrderMessageAdapter;
import com.ipd.taxiu.adapter.OtherMessageAdapter;
import com.ipd.taxiu.adapter.SystemMessageAdapter;
import com.ipd.taxiu.bean.OrderMessageBean;
import com.ipd.taxiu.bean.MessageListBean;
import com.ipd.taxiu.bean.SystemMessageBean;
import com.ipd.taxiu.ui.ListFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Miss on 2018/7/19
 * 消息
 */
public class MessageFragment<T> extends ListFragment<MessageListBean, T> {
    private OrderMessageAdapter mAdapter = null;
    private SystemMessageAdapter systemMessageAdapter= null;
    private OtherMessageAdapter otherMessageAdapter = null;

    private Bundle args;
    private int categoryId;

    public static MessageFragment newInstance(int categoryId) {
        MessageFragment fragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", categoryId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        progress_layout.setEmptyViewRes(R.layout.layout_empty_message);
    }


    @Override
    public int isNoMoreData(MessageListBean result) {
        if (result.list == null || result.list.isEmpty()) {
            return getEMPTY_DATA();
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        if (categoryId == 0) {
            if (mAdapter == null) {
                mAdapter = new OrderMessageAdapter(getContext(), (ArrayList<OrderMessageBean>) getData());
                recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
                recycler_view.setAdapter(mAdapter);
            } else {
                mAdapter.notifyDataSetChanged();
            }
        }else if (categoryId == 1){
            if (systemMessageAdapter == null) {
                systemMessageAdapter = new SystemMessageAdapter(getContext(), (ArrayList<SystemMessageBean>) getData());
                recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
                recycler_view.setAdapter(systemMessageAdapter);
            } else {
                systemMessageAdapter.notifyDataSetChanged();
            }
        }else if (categoryId == 2){
            if (otherMessageAdapter == null) {
                otherMessageAdapter = new OtherMessageAdapter(getContext(), (ArrayList<String>) getData());
                recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
                recycler_view.setAdapter(otherMessageAdapter);
            } else {
                otherMessageAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void addData(boolean isRefresh, MessageListBean result) {
        getData().addAll(result.list);
    }

    @NotNull
    @Override
    public Observable<MessageListBean> loadListData() {
        args = getArguments();
        categoryId = args.getInt("categoryId");
        return Observable.create(new Observable.OnSubscribe<MessageListBean>() {
            @Override
            public void call(Subscriber<? super MessageListBean> subscriber) {
                MessageListBean bean = new MessageListBean();
                bean.list = new ArrayList<>();
                if (categoryId == 0) {
                    for (int i = 0;i < 4;i++) {
                        if (i== 0) {
                            bean.list.add(new OrderMessageBean(1));
                        }
                        if (i== 1) {
                            bean.list.add(new OrderMessageBean(2));
                        }
                        if (i== 2) {
                            bean.list.add(new OrderMessageBean(3));
                        }
                        if (i== 3) {
                            bean.list.add(new OrderMessageBean(4));
                        }
                    }
                }else if (categoryId == 1) {
                    for (int i = 0;i < 4;i++) {
                        if (i== 0) {
                            bean.list.add(new SystemMessageBean(1));
                        }else {
                            bean.list.add(new SystemMessageBean(2));
                        }
                    }
                }else if (categoryId == 2){
                    bean.list.add(new ArrayList<>().add(""));
                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        });
    }

}
