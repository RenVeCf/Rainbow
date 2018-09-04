package com.ipd.taxiu.ui.fragment.address;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.DeliveryAddressAdapter;
import com.ipd.taxiu.bean.AddressBean;
import com.ipd.taxiu.bean.BaseResult;
import com.ipd.taxiu.platform.global.GlobalParam;
import com.ipd.taxiu.platform.http.ApiManager;
import com.ipd.taxiu.ui.ListFragment;
import com.ipd.taxiu.ui.activity.address.AddAddressActivity;
import com.ipd.taxiu.widget.MessageDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Miss on 2018/8/17
 */
public class DeliveryAddressFragment extends ListFragment<List<AddressBean>, AddressBean> {
    private DeliveryAddressAdapter mAdapter;
    private View view;
    private List<AddressBean> bean;


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
        view = progress_layout.getEmptyViewRes(R.layout.layout_empty_address);
    }


    @Override
    public int isNoMoreData(List<AddressBean> result) {
        if (result == null || result.isEmpty()){
            if (getPage() == getINIT_PAGE()){
                return getEMPTY_DATA();
            }else {
                return getNO_MORE_DATA();
            }
        }
        return getNORMAL();
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
        view.findViewById(R.id.btn_add_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAddressActivity.Companion.launch(getMActivity(), 1, "");
            }
        });

        getMRootView().findViewById(R.id.btn_add_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAddressActivity.Companion.launch(getMActivity(), 1, "");
            }
        });

    }


    @NotNull
    @Override
    public Observable<List<AddressBean>> loadListData() {
        return ApiManager.getService().getListAddress(10, GlobalParam.getUserId(), getPage())
                .map(new Func1<BaseResult<List<AddressBean>>, List<AddressBean>>() {
                    @Override
                    public List<AddressBean> call(BaseResult<List<AddressBean>> listBaseResult) {
                        bean = new ArrayList<>();
                        if (listBaseResult.code == 0) {
                            bean.addAll(listBaseResult.data);
                        }
                        return bean;
                    }
                });
    }
}
