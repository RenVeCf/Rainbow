package com.ipd.taxiu.ui.fragment.balance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.BankCardAdapter;
import com.ipd.taxiu.bean.BankCardBean;
import com.ipd.taxiu.ui.ListFragment;
import com.ipd.taxiu.ui.activity.balance.AddBankCardActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Miss on 2018/8/6
 */
public class BankCardFragment extends ListFragment<List<BankCardBean>, BankCardBean> implements View.OnClickListener {
    private BankCardAdapter mAdapter = null;
    private TextView btn_add_card;
    private List<BankCardBean> bean = new ArrayList<>();

    public static BankCardFragment newInstance(String bankType) {
        BankCardFragment fragment = new BankCardFragment();
        Bundle bundle = new Bundle();
        bundle.putString("bankType", bankType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.layout_bank_card;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        progress_layout.setEmptyViewRes(R.layout.layout_empty_bank);
        View view = progress_layout.getEmptyViewRes(R.layout.layout_empty_bank);
        initData(bean);
        if (bean.size() == 0 || bean == null) {
            btn_add_card = view.findViewById(R.id.btn_add_card);
        }else {
            btn_add_card = getMRootView().findViewById(R.id.btn_add_card);
        }
    }

    @NotNull
    @Override
    public Observable<List<BankCardBean>> loadListData() {
        return Observable.create(new Observable.OnSubscribe<List<BankCardBean>>() {
            @Override
            public void call(Subscriber<? super List<BankCardBean>> subscriber) {
                subscriber.onNext(bean);
                subscriber.onCompleted();
            }
        });
    }

    private void initData(List<BankCardBean> bean) {
        BankCardBean bean1 = new BankCardBean(R.mipmap.bank_of_china, "中国银行");
        BankCardBean bean2 = new BankCardBean(R.mipmap.agricultural_cank_of_china, "中国农业银行");
        BankCardBean bean3 = new BankCardBean(R.mipmap.communications_of_china, "中国交通银行");
        BankCardBean bean4 = new BankCardBean(R.mipmap.construction_bank_of_china, "中国建设银行");
        bean.add(bean1);
        bean.add(bean2);
        bean.add(bean3);
        bean.add(bean4);
    }

    @Override
    public int isNoMoreData(List<BankCardBean> result) {
        if (result == null || result.isEmpty()) {
            return getEMPTY_DATA();
        } else {
            return getNORMAL();
        }
    }

    @Override
    public void setOrNotifyAdapter() {
        Bundle args = getArguments();
        String bankType = args.getString("bankType");
        if (mAdapter == null) {
            mAdapter = new BankCardAdapter(getContext(), getData(), bankType);
            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, List<BankCardBean> result) {
        getData().addAll(result);
    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_add_card.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_card:
                AddBankCardActivity.Companion.launch(getMActivity());
                break;
        }
    }
}
