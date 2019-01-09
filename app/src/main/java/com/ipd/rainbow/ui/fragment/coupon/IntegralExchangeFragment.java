package com.ipd.rainbow.ui.fragment.coupon;

import android.support.v7.widget.GridLayoutManager;

import com.ipd.rainbow.adapter.IntegralExchangeAdapter;
import com.ipd.rainbow.bean.BaseResult;
import com.ipd.rainbow.bean.ExchangeBean;
import com.ipd.rainbow.platform.global.Constant;
import com.ipd.rainbow.platform.global.GlobalParam;
import com.ipd.rainbow.platform.http.ApiManager;
import com.ipd.rainbow.ui.ListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Miss on 2018/8/6
 */
public class IntegralExchangeFragment extends ListFragment<List<ExchangeBean>, ExchangeBean> {
    private IntegralExchangeAdapter mAdapter = null;

    public static IntegralExchangeFragment newInstance() {
        IntegralExchangeFragment fragment = new IntegralExchangeFragment();
        return fragment;
    }

    @NotNull
    @Override
    public Observable<List<ExchangeBean>> loadListData() {
        return ApiManager.getService().scoreExchangeList(Constant.PAGE_SIZE, GlobalParam.getUserId(), getPage())
                .map(new Func1<BaseResult<List<ExchangeBean>>, List<ExchangeBean>>() {
                    @Override
                    public List<ExchangeBean> call(BaseResult<List<ExchangeBean>> listBaseResult) {
                        List<ExchangeBean> bean = new ArrayList<>();
                        if (listBaseResult.code == 0) {
                            bean.addAll(listBaseResult.data);
                        }
                        return bean;
                    }
                });
    }

    @Override
    public int isNoMoreData(List<ExchangeBean> result) {
        if (result == null || result.isEmpty()) {
            if (getPage() == getINIT_PAGE()) {
                return getEMPTY_DATA();
            } else {
                return getNO_MORE_DATA();
            }
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new IntegralExchangeAdapter(getContext(), getData());
            recycler_view.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, List<ExchangeBean> result) {
        getData().addAll(result);
    }
}
