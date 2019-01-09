package com.ipd.rainbow.ui.fragment.coupon;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.ipd.rainbow.R;
import com.ipd.rainbow.adapter.ExchangeRecordAdapter;
import com.ipd.rainbow.bean.BaseResult;
import com.ipd.rainbow.bean.ExchangeHisBean;
import com.ipd.rainbow.platform.global.Constant;
import com.ipd.rainbow.platform.global.GlobalParam;
import com.ipd.rainbow.platform.http.ApiManager;
import com.ipd.rainbow.ui.ListFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Miss on 2018/8/6
 */
public class ExchangeRecordFragment extends ListFragment<List<ExchangeHisBean>,ExchangeHisBean> {
    private ExchangeRecordAdapter mAdapter = null;

    public static ExchangeRecordFragment newInstance() {
        ExchangeRecordFragment fragment = new ExchangeRecordFragment();
        return fragment;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        progress_layout.setEmptyViewRes(R.layout.layout_empty_record);
    }

    @NotNull
    @Override
    public Observable<List<ExchangeHisBean>> loadListData() {
        return ApiManager.getService().exchangeHis(Constant.PAGE_SIZE, GlobalParam.getUserId(),getPage())
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
            if (getPage() ==getINIT_PAGE()) {
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
            mAdapter = new ExchangeRecordAdapter(getContext(),getData());
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
