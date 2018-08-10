package com.ipd.taxiu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.HomepageBean;
import com.ipd.taxiu.ui.activity.classroom.ClassRoomDetailActivity;
import com.ipd.taxiu.ui.activity.pet.PetInformationActivity;
import com.ipd.taxiu.ui.activity.taxiu.TaxiuDetailActivity;

import java.util.List;

/**
 * Created by Miss on 2018/7/25
 * 查看主页adapter
 */
public class HomepageAdapter extends RecyclerView.Adapter<HomepageAdapter.ViewHolder> {
    private Context context;
    private List<HomepageBean> datas;

    public enum ITEM_TYPE {
        ITEM1,
        ITEM2
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return ITEM_TYPE.ITEM1.ordinal();
            case 1:
                return ITEM_TYPE.ITEM1.ordinal();
        }
        return ITEM_TYPE.ITEM2.ordinal();
    }

    public HomepageAdapter(Context context, List<HomepageBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM1.ordinal()) {
            return new HomepageAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_friend_list1, parent, false));
        } else {
            return new HomepageAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_friend_list2, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == ITEM_TYPE.ITEM1.ordinal()) {
            if (holder.item1 == null) return;
            holder.item1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TaxiuDetailActivity.class);
                    context.startActivity(intent);
                }
            });
        } else {
            if (holder.item2 == null) return;
            holder.item2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TaxiuDetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout item1, item2;

        public ViewHolder(View itemView) {
            super(itemView);
            item1 = itemView.findViewById(R.id.item1);
            item2 = itemView.findViewById(R.id.item2);
        }
    }
}
