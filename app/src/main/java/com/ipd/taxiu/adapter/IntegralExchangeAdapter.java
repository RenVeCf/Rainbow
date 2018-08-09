package com.ipd.taxiu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.ExchangeBean;
import com.ipd.taxiu.ui.activity.coupon.CouponDetailActivity;
import com.ipd.taxiu.ui.activity.coupon.IntegralExchangeActivity;

import java.util.List;

/**
 * Created by Miss on 2018/7/20
 */
public class IntegralExchangeAdapter extends RecyclerView.Adapter<IntegralExchangeAdapter.ViewHolder> {

    private List<ExchangeBean> data;
    private Context mContext;

    public IntegralExchangeAdapter(List<ExchangeBean> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(View.inflate(mContext,R.layout.item_integral_exchange,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CouponDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rl_item;
        public ViewHolder(View itemView) {
            super(itemView);
            rl_item = itemView.findViewById(R.id.rl_item);
        }
    }
}
