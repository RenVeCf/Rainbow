package com.ipd.rainbow.ui.fragment.referral;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.ipd.rainbow.R;
import com.ipd.rainbow.adapter.FriendListAdapter;
import com.ipd.rainbow.bean.AttentionBean;
import com.ipd.rainbow.bean.BaseResult;
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
public class FriendListFragment extends ListFragment<List<AttentionBean>, AttentionBean> {
    private FriendListAdapter mAdapter = null;

    public static FriendListFragment newInstance() {
        FriendListFragment fragment = new FriendListFragment();
        return fragment;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        progress_layout.setEmptyViewRes(R.layout.layout_empty_friends);
    }

    @NotNull
    @Override
    public Observable<List<AttentionBean>> loadListData() {
        return ApiManager.getService().getFriendList(10, GlobalParam.getUserId(), getPage())
                .map(new Func1<BaseResult<List<AttentionBean>>, List<AttentionBean>>() {
                    @Override
                    public List<AttentionBean> call(BaseResult<List<AttentionBean>> listBaseResult) {
                        List<AttentionBean> list = new ArrayList<>();
                        if (listBaseResult.code == 0) {
                            list.addAll(listBaseResult.data);
                        }
                        return list;
                    }
                });
    }

    @Override
    public int isNoMoreData(List<AttentionBean> result) {
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
            mAdapter = new FriendListAdapter(getContext(), getData());
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
}
