package com.ipd.taxiu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.activity.coupon.CouponDetailActivity;
import com.ipd.taxiu.ui.activity.coupon.ExchangeRecordActivity;
import com.ipd.taxiu.ui.activity.coupon.ExchangeRecordDetailActivity;

import java.util.List;

/**
 * Created by Miss on 2018/7/20
 */
public class ExchangeRecordAdapter extends RecyclerView.Adapter<ExchangeRecordAdapter.ViewHolder> {

    private List<String> data;
    private Context mContext;

    public ExchangeRecordAdapter(List<String> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(View.inflate(mContext,R.layout.item_exchange_record,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ExchangeRecordDetailActivity.class);
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
            rl_item = itemView.findViewById(R.id.rl_exchange_record);
        }
    }
}
