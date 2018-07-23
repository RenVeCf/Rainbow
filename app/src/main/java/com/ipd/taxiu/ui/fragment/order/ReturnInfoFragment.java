package com.ipd.taxiu.ui.fragment.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.OrderListAdapter;
import com.ipd.taxiu.adapter.ReturnAdapter;
import com.ipd.taxiu.bean.OrderBean;
import com.ipd.taxiu.bean.OrderListBean;
import com.ipd.taxiu.bean.ReturnBean;
import com.ipd.taxiu.bean.ReturnListBean;
import com.ipd.taxiu.ui.ListFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Miss on 2018/7/19
 * 退款退货审核信息
 */
public class ReturnInfoFragment extends ListFragment<ReturnListBean, ReturnBean> {
    private ReturnAdapter mAdapter = null;

    public static ReturnInfoFragment newInstance(int categoryId) {
        ReturnInfoFragment fragment = new ReturnInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", categoryId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        progress_layout.setEmptyViewRes(R.layout.layout_empty_return);
    }


    @Override
    public int isNoMoreData(ReturnListBean result) {
        if (result.list == null || result.list.isEmpty()) {
            return getEMPTY_DATA();
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new ReturnAdapter(getContext(),getData());
            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, ReturnListBean result) {
        getData().addAll(result.list);
    }

    @NotNull
    @Override
    public Observable<ReturnListBean> loadListData() {
        final Bundle args = getArguments();
        final int categoryId = args.getInt("categoryId");
        return Observable.create(new Observable.OnSubscribe<ReturnListBean>() {
            @Override
            public void call(Subscriber<? super ReturnListBean> subscriber) {
                ReturnListBean bean = new ReturnListBean();
                bean.list = new ArrayList<>();
                if (categoryId == 0) {
                    for (int i = 0; i < 3; i++) {
                        if (i == 0) {
                            bean.list.add(new ReturnBean("审核中",2, 4));
                        }else if (i == 1){
                            bean.list.add(new ReturnBean("审核中",1, 2));
                        }else {
                            bean.list.add(new ReturnBean("审核中",1, 3));
                        }
                    }
                } else if (categoryId == 1) {
                    for (int i = 0; i < 3; i++) {
                        if (i == 0) {
                            bean.list.add(new ReturnBean("已通过",2, 4));
                        }else if (i == 1){
                            bean.list.add(new ReturnBean("已通过",1, 2));
                        }else {
                            bean.list.add(new ReturnBean("已通过",1, 3));
                        }
                    }
                }
//                else if (categoryId == 2) {
//                    for (int i = 0; i < 3; i++) {
//                        bean.list.add(new ReturnBean());
//                    }
//                }
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        });
    }

}
