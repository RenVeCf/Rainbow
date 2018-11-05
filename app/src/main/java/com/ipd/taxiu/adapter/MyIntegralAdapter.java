package com.ipd.taxiu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.IntegralBean;

import java.util.List;

/**
 * Created by Miss on 2018/7/20
 */
public class MyIntegralAdapter extends RecyclerView.Adapter<MyIntegralAdapter.ViewHolder> {

    private List<IntegralBean> data;
    private Context mContext;

    public MyIntegralAdapter(List<IntegralBean> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_integral, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.integral_title.setText(data.get(position).TITLE);
        holder.integral_explain.setText(data.get(position).CONTENT);
        holder.integral_time.setText(data.get(position).CREATETIME);
        int score = data.get(position).SCORE;
        if (score > 0) {
            holder.integral_money.setText("+" + score);
            holder.integral_money.setTextColor(mContext.getResources().getColor(R.color.earning_text));
        } else {
            holder.integral_money.setText("" + score);
            holder.integral_money.setTextColor(mContext.getResources().getColor(R.color.black));
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView integral_title;
        TextView integral_explain;
        TextView integral_time;
        TextView integral_money;

        public ViewHolder(View itemView) {
            super(itemView);
            integral_title = itemView.findViewById(R.id.integral_title);
            integral_explain = itemView.findViewById(R.id.integral_explain);
            integral_time = itemView.findViewById(R.id.integral_time);
            integral_money = itemView.findViewById(R.id.integral_money);
        }
    }
}
