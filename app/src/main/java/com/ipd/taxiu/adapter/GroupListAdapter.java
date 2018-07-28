package com.ipd.taxiu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.GroupBean;
import com.ipd.taxiu.bean.OrderBean;
import com.ipd.taxiu.ui.activity.group.GroupDetailActivity;
import com.ipd.taxiu.ui.activity.order.OrderDetailActivity;
import com.ipd.taxiu.widget.ChooseFriendDialog;

import java.util.ArrayList;

/**
 * Created by Miss on 2018/7/19
 */
public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<GroupBean> list;

    public GroupListAdapter(Context context, ArrayList<GroupBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_group_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final GroupListAdapter.ViewHolder holder, int position) {
        int status = list.get(position).getStatus();
        switch (status){
            case 1:
                holder.commodity_status.setText("待成团");
                holder.order_number.setText("5人团，还差2人");
                holder.tv_confirm.setText("邀请好友");
                holder.tv_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChooseFriendDialog dialog = new ChooseFriendDialog(context,R.style.recharge_pay_dialog,2);
                        dialog.show();
                    }
                });
                holder.tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(1);
                    }
                });
                holder.tv_cancel.setVisibility(View.VISIBLE);
                holder.tv_confirm.setBackgroundResource(R.drawable.shape_order_btn_confirm);
                holder.tv_confirm.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                break;
            case 2:
                holder.commodity_status.setText("已成团");
                holder.order_number.setText("5人团，还差0人");
                holder.tv_confirm.setText("详情");

                holder.tv_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(2);
                    }
                });

                holder.tv_cancel.setVisibility(View.GONE);
                holder.tv_confirm.setBackgroundResource(R.drawable.shape_order_btn_cancel);
                holder.tv_confirm.setTextColor(context.getResources().getColor(R.color.black));
                break;
            case 3:
                holder.commodity_status.setText("未成团");
                holder.order_number.setText("5人团，参团人数不足");
                holder.tv_confirm.setText("详情");
                holder.tv_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(3);
                    }
                });
                holder.tv_cancel.setVisibility(View.GONE);
                holder.tv_confirm.setBackgroundResource(R.drawable.shape_order_btn_cancel);
                holder.tv_confirm.setTextColor(context.getResources().getColor(R.color.black));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView commodity_status,order_number,tv_cancel,tv_confirm;
        public ViewHolder(View itemView) {
            super(itemView);
            commodity_status = itemView.findViewById(R.id.commodity_status);
            order_number = itemView.findViewById(R.id.order_number);
            tv_cancel = itemView.findViewById(R.id.tv_cancel);
            tv_confirm = itemView.findViewById(R.id.tv_confirm);
        }

    }

    private void startActivity(int GroupStatus){
        Intent intent = new Intent(context, GroupDetailActivity.class);
        intent.putExtra("GroupStatus",GroupStatus);
        context.startActivity(intent);
    }

}
