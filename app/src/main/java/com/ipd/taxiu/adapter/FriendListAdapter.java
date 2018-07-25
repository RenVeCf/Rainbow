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
import com.ipd.taxiu.bean.FriendListBean;
import com.ipd.taxiu.ui.activity.referral.HomepageActivity;

import java.util.List;

/**
 * Created by Miss on 2018/7/25
 * 好友列表adapter
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    private Context context;
    private List<FriendBean> datas;

    public FriendListAdapter(Context context, List<FriendBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendListAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_friend_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
        RelativeLayout rl_friend_list;
        public ViewHolder(View itemView) {
            super(itemView);
            rl_friend_list = itemView.findViewById(R.id.rl_friend_list);
        }
    }
}
