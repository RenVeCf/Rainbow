package com.ipd.taxiu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.OrderDetailBean;

import java.util.List;
import java.util.zip.Inflater;

import static com.ipd.taxiu.adapter.OrderListAdapter.DELIVERY;
import static com.ipd.taxiu.adapter.OrderListAdapter.DETAIL;
import static com.ipd.taxiu.adapter.OrderListAdapter.EVALUATE;
import static com.ipd.taxiu.adapter.OrderListAdapter.PAYMENT;

/**
 * Created by Miss on 2018/7/20
 */
public class MyIntegralAdapter extends RecyclerView.Adapter<MyIntegralAdapter.ViewHolder> {

    private List<String> data;
    private Context mContext;

    public MyIntegralAdapter(List<String> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(View.inflate(mContext,R.layout.item_integral,null));
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
