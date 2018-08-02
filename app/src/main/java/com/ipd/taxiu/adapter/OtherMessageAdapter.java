package com.ipd.taxiu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.OrderMessageBean;
import com.ipd.taxiu.ui.activity.message.MessageDetailActivity;

import java.util.ArrayList;

/**
 * Created by Miss on 2018/7/19
 * 消息adapter
 */
public class OtherMessageAdapter extends RecyclerView.Adapter<OtherMessageAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> list;

    public OtherMessageAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_other_message, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final OtherMessageAdapter.ViewHolder holder, int position) {
        holder.tv_message_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(context,MessageDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView tv_message_time;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_message_time = itemView.findViewById(R.id.tv_message_time);
        }

    }

}
