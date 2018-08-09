package com.ipd.taxiu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.CouponBean;
import com.ipd.taxiu.ui.activity.coupon.ExchangeRecordDetailActivity;

import java.util.List;

/**
 * Created by Miss on 2018/7/20
 */
public class DiscountExchangeAdapter extends RecyclerView.Adapter<DiscountExchangeAdapter.ViewHolder> {

    private List<CouponBean> data;
    private Context mContext;

    public DiscountExchangeAdapter(List<CouponBean> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(View.inflate(mContext,R.layout.item_discount_exchange,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
