package com.ipd.taxiu.ui.fragment.coupon;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.DiscountExchangeAdapter;
import com.ipd.taxiu.bean.BaseResult;
import com.ipd.taxiu.bean.ExchangeHisBean;
import com.ipd.taxiu.platform.global.Constant;
import com.ipd.taxiu.platform.global.GlobalParam;
import com.ipd.taxiu.platform.http.ApiManager;
import com.ipd.taxiu.ui.ListFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Miss on 2018/8/6
 */
public class DiscountCouponFragment extends ListFragment<List<ExchangeHisBean>,ExchangeHisBean> {
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
    public Observable<List<ExchangeHisBean>> loadListData() {
        return ApiManager.getService().couponList(Constant.PAGE_SIZE, GlobalParam.getUserId(),getPage(),0)
                .map(new Func1<BaseResult<List<ExchangeHisBean>>, List<ExchangeHisBean>>() {
                    @Override
                    public List<ExchangeHisBean> call(BaseResult<List<ExchangeHisBean>> listBaseResult) {
                        List<ExchangeHisBean> list = new ArrayList<>();
                        if (listBaseResult.code == 0){
                            list.addAll(listBaseResult.data);
                        }
                        return list;
                    }
                });
    }

    @Override
    public int isNoMoreData(List<ExchangeHisBean> result) {
        if (result == null || result.isEmpty()) {
            if (getPage() == getINIT_PAGE()) {
                return getEMPTY_DATA();
            }else {
                return getNO_MORE_DATA();
            }
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new DiscountExchangeAdapter(getContext(),getData());
            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, List<ExchangeHisBean> result) {
        getData().addAll(result);
    }
}
