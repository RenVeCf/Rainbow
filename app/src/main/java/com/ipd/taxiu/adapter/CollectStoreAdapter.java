package com.ipd.taxiu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.CollectStoreBean;
import com.ipd.taxiu.ui.activity.pet.PetInformationActivity;

import java.util.List;

/**
 * Created by Miss on 2018/7/25
 * 收藏-商品adapter
 */
public class CollectStoreAdapter extends RecyclerView.Adapter<CollectStoreAdapter.ViewHolder> {
    private Context context;
    private List<CollectStoreBean> datas;

    public CollectStoreAdapter(Context context, List<CollectStoreBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CollectStoreAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_collect, parent, false));
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
