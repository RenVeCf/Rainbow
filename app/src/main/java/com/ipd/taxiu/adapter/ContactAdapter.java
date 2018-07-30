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
import com.ipd.taxiu.bean.CollectStoreBean;
import com.ipd.taxiu.bean.ContactBean;
import com.ipd.taxiu.ui.activity.referral.FriendListActivity;
import com.ipd.taxiu.ui.activity.referral.HomepageActivity;

import java.util.List;

/**
 * Created by Miss on 2018/7/25
 * 收藏-商品adapter
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private Context context;
    private List<ContactBean> datas;

    public ContactAdapter(Context context, List<ContactBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (datas.get(position).getStatus() == 1){
            holder.done_attention.setVisibility(View.VISIBLE);
            holder.add_attention.setVisibility(View.GONE);
            holder.attention.setVisibility(View.GONE);
        }else if (datas.get(position).getStatus() == 2){
            holder.done_attention.setVisibility(View.GONE);
            holder.add_attention.setVisibility(View.VISIBLE);
            holder.attention.setVisibility(View.GONE);
        }else {
            holder.done_attention.setVisibility(View.GONE);
            holder.add_attention.setVisibility(View.GONE);
            holder.attention.setVisibility(View.VISIBLE);
        }
        holder.rl_friend_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomepageActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout add_attention,done_attention,attention;
        RelativeLayout rl_friend_list;
        public ViewHolder(View itemView) {
            super(itemView);
            add_attention = itemView.findViewById(R.id.add_attention);
            done_attention = itemView.findViewById(R.id.done_attention);
            attention = itemView.findViewById(R.id.attention);
            rl_friend_list = itemView.findViewById(R.id.rl_friend_list);
        }
    }
}
