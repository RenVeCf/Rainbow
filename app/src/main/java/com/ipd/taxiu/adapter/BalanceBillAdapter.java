package com.ipd.taxiu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.BalanceBillBean;

import java.util.List;

/**
 * Created by Miss on 2018/7/20
 */
public class BalanceBillAdapter extends RecyclerView.Adapter<BalanceBillAdapter.ViewHolder> {

    private List<BalanceBillBean> data;
    private Context mContext;

    public BalanceBillAdapter(List<BalanceBillBean> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(View.inflate(mContext,R.layout.item_balance_bill,null));
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
