package com.ipd.taxiu.ui.fragment.collect;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.ContactAdapter;
import com.ipd.taxiu.bean.AttentionBean;
import com.ipd.taxiu.bean.BaseResult;
import com.ipd.taxiu.bean.ContactBean;
import com.ipd.taxiu.bean.ContactListBean;
import com.ipd.taxiu.bean.UserBean;
import com.ipd.taxiu.platform.global.GlobalParam;
import com.ipd.taxiu.platform.http.ApiManager;
import com.ipd.taxiu.presenter.MinePresenter;
import com.ipd.taxiu.ui.ListFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Miss on 2018/7/19
 * 社交
 */
public class SocicalContactFragment extends ListFragment<List<AttentionBean>, AttentionBean> implements MinePresenter.IAttentionView {
    private ContactAdapter mAdapter = null;

    private MinePresenter mPresenter;

    public static SocicalContactFragment newInstance(int TYPE) {
        SocicalContactFragment fragment = new SocicalContactFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE", TYPE);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onViewDetach() {
        super.onViewDetach();
        mPresenter.detachView();
        mPresenter = null;
    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        mPresenter = new MinePresenter();
        mPresenter.attachView(getContext(), this);
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        progress_layout.setEmptyViewRes(R.layout.layout_empty_contact);
    }

    @NotNull
    @Override
    public Observable<List<AttentionBean>> loadListData() {
        Bundle bundle = getArguments();
        final int type = bundle.getInt("TYPE");
        return ApiManager.getService().attentionList(10, GlobalParam.getUserId(), getPage(), type)
                .map(new Func1<BaseResult<List<AttentionBean>>, List<AttentionBean>>() {
                    @Override
                    public List<AttentionBean> call(BaseResult<List<AttentionBean>> listBaseResult) {
                        List<AttentionBean> fans = new ArrayList<>();
                        if (listBaseResult.code == 0) {
                            fans.addAll(listBaseResult.data);
                        }
                        return fans;
                    }
                });
    }

    @Override
    public int isNoMoreData(List<AttentionBean> result) {
        return getNORMAL();
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new ContactAdapter(getContext(), getData(), mPresenter);
            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, List<AttentionBean> result) {
        getData().addAll(result);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail(@NotNull String errMsg) {
        toastShow(errMsg);
    }
}
