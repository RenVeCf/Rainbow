package com.ipd.taxiu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ipd.taxiu.R;

import java.util.List;

/**
 * Created by Miss on 2018/7/25
 * 推荐收益明细adapter
 */
public class MyPetAdapter extends RecyclerView.Adapter<MyPetAdapter.ViewHolder> {
    private Context context;
    private List<String> datas;

    public MyPetAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyPetAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_pet, parent, false));
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
