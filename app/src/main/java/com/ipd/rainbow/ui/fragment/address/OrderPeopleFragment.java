package com.ipd.rainbow.ui.fragment.address;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ipd.rainbow.R;
import com.ipd.rainbow.adapter.OrderPeopleAdapter;
import com.ipd.rainbow.bean.BaseResult;
import com.ipd.rainbow.bean.OrderPeopleBean;
import com.ipd.rainbow.event.ChooseOrderPeopleEvent;
import com.ipd.rainbow.event.UpdateOrderPeopleEvent;
import com.ipd.rainbow.platform.global.GlobalParam;
import com.ipd.rainbow.platform.http.ApiManager;
import com.ipd.rainbow.ui.ListFragment;
import com.ipd.rainbow.ui.activity.address.AddOrderPeopleActivity;
import com.ipd.rainbow.ui.activity.address.DeliveryAddressActivity;
import com.ipd.rainbow.ui.activity.address.OrderPeopleActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Miss on 2018/8/17
 */
public class OrderPeopleFragment extends ListFragment<List<OrderPeopleBean>, OrderPeopleBean> {
    private OrderPeopleAdapter mAdapter;
    private View view;
    private List<OrderPeopleBean> bean;


    public static OrderPeopleFragment newInstance(int type) {
        OrderPeopleFragment fragment = new OrderPeopleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_order_people;
    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onViewDetach() {
        super.onViewDetach();
        EventBus.getDefault().unregister(this);
    }

    private int mType;

    @Override
    protected void initView(@Nullable Bundle bundle) {
        super.initView(bundle);
        mType = getArguments().getInt("type", DeliveryAddressActivity.Companion.getNORMAL());
        progress_layout.setEmptyViewRes(R.layout.layout_empty_order_people);
        view = progress_layout.getEmptyViewRes(R.layout.layout_empty_order_people);
    }


    @Override
    public int isNoMoreData(List<OrderPeopleBean> result) {
        if (result == null || result.isEmpty()) {
            if (getPage() == getINIT_PAGE()) {
                return getEMPTY_DATA();
            } else {
                return getNO_MORE_DATA();
            }
        }
        return getNORMAL();
    }

    @Override
    public void setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = new OrderPeopleAdapter(getContext(), getData(), new Function1<OrderPeopleBean, Unit>() {
                @Override
                public Unit invoke(OrderPeopleBean addressBean) {
                    if (mType == OrderPeopleActivity.Companion.getNORMAL()) {
                        AddOrderPeopleActivity.Companion.launch(getMActivity(), 2, addressBean.SUBSCRIBER_ID);
                    } else if (mType == OrderPeopleActivity.Companion.getCHOOSE()) {
                        //选择地址
                        EventBus.getDefault().post(new ChooseOrderPeopleEvent(addressBean));
                        getMActivity().finish();
                    }
                    return null;
                }
            });
            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_view.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(boolean isRefresh, List<OrderPeopleBean> result) {
        getData().addAll(result);
    }

    @Override
    protected void initListener() {
        super.initListener();
        view.findViewById(R.id.btn_add_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddOrderPeopleActivity.Companion.launch(getMActivity(), 1, "");
            }
        });

        getMRootView().findViewById(R.id.btn_add_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddOrderPeopleActivity.Companion.launch(getMActivity(), 1, "");
            }
        });

    }


    @NotNull
    @Override
    public Observable<List<OrderPeopleBean>> loadListData() {
        return ApiManager.getService().getListOrderPeople(10, GlobalParam.getUserId(), getPage())
                .map(new Func1<BaseResult<List<OrderPeopleBean>>, List<OrderPeopleBean>>() {
                    @Override
                    public List<OrderPeopleBean> call(BaseResult<List<OrderPeopleBean>> listBaseResult) {
                        bean = new ArrayList<>();
                        if (listBaseResult.code == 0) {
                            bean.addAll(listBaseResult.data);
                        }
                        return bean;
                    }
                });
    }

    @Subscribe
    public void onMainEvent(UpdateOrderPeopleEvent event) {
        setCreate(true);
        onRefresh();
    }
}
