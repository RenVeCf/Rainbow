package com.ipd.taxiu.ui.fragment.referral;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.FriendListAdapter;
import com.ipd.taxiu.bean.BaseResult;
import com.ipd.taxiu.bean.UserBean;
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
public class FriendListFragment extends ListFragment<List<UserBean>,UserBean> {
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
    public Observable<List<UserBean>> loadListData() {
        return ApiManager.getService().getFriendList(10,"1",getPage())
                .map(new Func1<BaseResult<List<UserBean>>, List<UserBean>>() {
                    @Override
                    public List<UserBean> call(BaseResult<List<UserBean>> listBaseResult) {
                        List<UserBean> list = new ArrayList<>();
                        if (listBaseResult.code == 0) {
                            list.addAll(listBaseResult.data);
                        }
                        return list;
                    }
                });
    }

    @Override
    public int isNoMoreData(List<UserBean> result) {
        if (result == null || result.isEmpty()) {
            return getEMPTY_DATA();
        } else {
            return getNORMAL();
        }
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
    public void addData(boolean isRefresh, List<UserBean> result) {
        getData().addAll(result);
    }
}
