package com.ipd.taxiu.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseFragment;
import com.ipd.taxiu.ui.activity.message.MessageActivity;
import com.ipd.taxiu.ui.activity.order.MyOrderActivity;
import com.ipd.taxiu.ui.activity.order.ReturnMoneyCommodityActivity;
import com.ipd.taxiu.ui.activity.setting.SettingActivity;

import org.jetbrains.annotations.Nullable;


/**
 * Created by Miss on 2018/7/19
 */
public class PersonFragment extends BaseFragment implements View.OnClickListener{
    private RelativeLayout rl_all_order,rl_wait_pay,rl_wait_shipments,rl_wait_delivery,rl_off_the_stocks;
    private RelativeLayout rl_return_record,rl_setting,rl_message;

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
        rl_return_record.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        rl_message.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), MyOrderActivity.class);
        Intent intent1;
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
            case R.id.rl_return_record:
                intent1 = new Intent(getActivity(), ReturnMoneyCommodityActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_setting:
                intent1 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_message:
                intent1 = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent1);
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
        rl_return_record = rootView.findViewById(R.id.rl_return_record);
        rl_setting = rootView.findViewById(R.id.rl_setting);
        rl_message = rootView.findViewById(R.id.rl_message);
    }
}
