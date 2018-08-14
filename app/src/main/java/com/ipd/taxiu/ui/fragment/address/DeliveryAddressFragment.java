package com.ipd.taxiu.ui.fragment.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.DeliveryAddressAdapter;
import com.ipd.taxiu.bean.AddressBean;
import com.ipd.taxiu.ui.ListFragment;
import com.ipd.taxiu.ui.activity.address.AddAddressActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Miss on 2018/8/6
 */
public class DeliveryAddressFragment extends ListFragment<List<AddressBean>, AddressBean> implements View.OnClickListener {
    private DeliveryAddressAdapter mAdapter = null;
    private TextView btn_add_address;
    private List<AddressBean> bean = new ArrayList<>();

    public static DeliveryAddressFragment newInstance() {
        DeliveryAddressFragment fragment = new DeliveryAddressFragment();
        return fragment;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_delivery_address;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        progress_layout.setEmptyViewRes(R.layout.layout_empty_address);
        View view = progress_layout.getEmptyViewRes(R.layout.layout_empty_address);
        initData(bean);
        if (bean.size() == 0 || bean == null) {
            btn_add_address = view.findViewById(R.id.btn_add_address);
        }else {
            btn_add_address = getMRootView().findViewById(R.id.btn_add_address);
        }

    }

    private void initData(List<AddressBean> bean) {
        for (int i = 0; i < 5; i++) {
            bean.add(new AddressBean());
        }
    }

    @NotNull
    @Override
    public Observable<List<AddressBean>> loadListData() {
        return Observable.create(new Observable.OnSubscribe<List<AddressBean>>() {
            @Override
            public void call(Subscriber<? super List<AddressBean>> subscriber) {
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public int isNoMoreData(List<AddressBean> result) {
        if (result == null || result.isEmpty()) {
            return getEMPTY_DATA();
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new DeliveryAddressAdapter(getContext(), getData());
            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, List<AddressBean> result) {
        getData().addAll(result);
    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_add_address.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_address:
                AddAddressActivity.Companion.launch(getMActivity(),"添加地址");
                break;
        }
    }
}
