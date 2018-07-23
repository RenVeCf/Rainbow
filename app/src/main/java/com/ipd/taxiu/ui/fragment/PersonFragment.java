package com.ipd.taxiu.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseFragment;
import com.ipd.taxiu.ui.BaseUIFragment;
import com.ipd.taxiu.ui.activity.order.MyOrderActivity;
import com.ipd.taxiu.widget.MessageDialog;

import org.jetbrains.annotations.Nullable;


/**
 * Created by Miss on 2018/7/19
 */
public class PersonFragment extends BaseFragment implements View.OnClickListener{
    private RelativeLayout rl_all_order,rl_wait_pay,rl_wait_shipments,rl_wait_delivery,rl_off_the_stocks;

    @Override
    protected int getBaseLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        rl_all_order.setOnClickListener(this);
        rl_wait_pay.setOnClickListener(this);
        rl_wait_shipments.setOnClickListener(this);
        rl_wait_delivery.setOnClickListener(this);
        rl_off_the_stocks.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), MyOrderActivity.class);;
        switch (v.getId()){
            case R.id.rl_all_order:
                intent.putExtra("status",0);
                startActivity(intent);
                break;
            case R.id.rl_wait_pay:
                intent.putExtra("status",1);
                startActivity(intent);
                break;
            case R.id.rl_wait_shipments:
                intent.putExtra("status",2);
                startActivity(intent);
                break;
            case R.id.rl_wait_delivery:
                intent.putExtra("status",3);
                startActivity(intent);
                break;
            case R.id.rl_off_the_stocks:
                intent.putExtra("status",4);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void initBaseLayout(@Nullable ViewGroup rootView) {
        super.initBaseLayout(rootView);
        rl_all_order = rootView.findViewById(R.id.rl_all_order);
        rl_wait_pay = rootView.findViewById(R.id.rl_wait_pay);
        rl_wait_shipments = rootView.findViewById(R.id.rl_wait_shipments);
        rl_wait_delivery = rootView.findViewById(R.id.rl_wait_delivery);
        rl_off_the_stocks = rootView.findViewById(R.id.rl_off_the_stocks);
    }
}
