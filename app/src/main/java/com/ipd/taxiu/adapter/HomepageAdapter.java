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
 * 查看主页adapter
 */
public class HomepageAdapter extends RecyclerView.Adapter<HomepageAdapter.ViewHolder> {
    private Context context;
    private List<String> datas;

    public enum ITEM_TYPE {
        ITEM1,
        ITEM2
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return ITEM_TYPE.ITEM1.ordinal();
        }
        return ITEM_TYPE.ITEM2.ordinal();
    }

    public HomepageAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM1.ordinal()) {
            return new HomepageAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_friend_list1, parent, false));
        }else {
            return new HomepageAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_friend_list2, parent, false));
        }
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
