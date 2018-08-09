package com.ipd.taxiu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.FriendBean;
import com.ipd.taxiu.bean.RecommendEarningsBean;
import com.ipd.taxiu.ui.activity.referral.HomepageActivity;

import java.util.List;

/**
 * Created by Miss on 2018/7/25
 * 推荐收益明细adapter
 */
public class EarningDetailAdapter extends RecyclerView.Adapter<EarningDetailAdapter.ViewHolder> {
    private Context context;
    private List<RecommendEarningsBean> datas;

    public EarningDetailAdapter(Context context, List<RecommendEarningsBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EarningDetailAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_earning_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
