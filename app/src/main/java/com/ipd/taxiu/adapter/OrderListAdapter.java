package com.ipd.taxiu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.OrderBean;
import com.ipd.taxiu.ui.activity.order.LogisticsDetailActivity;
import com.ipd.taxiu.ui.activity.order.OrderDetailActivity;

import java.util.ArrayList;

/**
 * Created by Miss on 2018/7/19
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    public static String PAYMENT = "付款";
    public static String DETAIL = "详情";
    public static String DELIVERY = "收货";
    public static String EVALUATE = "评价";

    private Context context;
    private ArrayList<OrderBean> list;

    public OrderListAdapter(Context context, ArrayList<OrderBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final OrderListAdapter.ViewHolder holder, int position) {
        switch (list.get(position).getStatus()) {
            case 1:
                holder.order_status.setText("待付款");
                holder.confirm.setText("付款");
                holder.cancel.setText("取消");
                holder.confirm.setVisibility(View.VISIBLE);
                holder.cancel.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.order_status.setText("待发货");
                holder.cancel.setVisibility(View.GONE);
                holder.confirm.setText("详情");
                holder.confirm.setVisibility(View.VISIBLE);
                holder.confirm.setBackgroundResource(R.drawable.shape_order_btn_cancel);
                holder.confirm.setTextColor(context.getResources().getColor(R.color.black));
                break;
            case 3:
                holder.order_status.setText("待收货");
                holder.confirm.setText("收货");
                holder.cancel.setText("物流");
                holder.confirm.setVisibility(View.VISIBLE);
                holder.cancel.setVisibility(View.VISIBLE);
                break;
            case 4:
                holder.order_status.setText("已完成");
                holder.confirm.setText("评价");
                holder.cancel.setText("再次购买");
                holder.confirm.setVisibility(View.VISIBLE);
                holder.cancel.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        final String confirmText = holder.confirm.getText().toString();
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (confirmText.equals("付款")) {
                    intent = new Intent(context, OrderDetailActivity.class);
                    intent.putExtra("order_status", PAYMENT);
                    context.startActivity(intent);
                } else if (confirmText.equals("详情")) {
                    intent = new Intent(context, OrderDetailActivity.class);
                    intent.putExtra("order_status", DETAIL);
                    context.startActivity(intent);
                } else if (confirmText.equals("收货")) {
                    intent = new Intent(context, OrderDetailActivity.class);
                    intent.putExtra("order_status", DELIVERY);
                    context.startActivity(intent);
                } else if (confirmText.equals("评价")) {
                    intent = new Intent(context, OrderDetailActivity.class);
                    intent.putExtra("order_status", EVALUATE);
                    context.startActivity(intent);
                }
            }
        });

        final String cancelText = holder.cancel.getText().toString();
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelText.equals("取消")) {
                } else if (cancelText.equals("物流")) {
                    Intent intent = new Intent(context,LogisticsDetailActivity.class);
                    context.startActivity(intent);
                } else if (cancelText.equals("再次购买")) {
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView order_status, confirm, cancel;

        public ViewHolder(View itemView) {
            super(itemView);
            order_status = itemView.findViewById(R.id.commodity_status);
            confirm = itemView.findViewById(R.id.tv_confirm);
            cancel = itemView.findViewById(R.id.tv_cancel);
        }

    }

}
