package com.ipd.taxiu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.activity.pet.PetInformationActivity;

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
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PetInformationActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_item;
        public ViewHolder(View itemView) {
            super(itemView);
            rl_item = itemView.findViewById(R.id.rl_item);
        }
    }
}
