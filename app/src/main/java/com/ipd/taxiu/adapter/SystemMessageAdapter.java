package com.ipd.taxiu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.OrderMessageBean;
import com.ipd.taxiu.bean.SystemMessageBean;

import java.util.ArrayList;

/**
 * Created by Miss on 2018/7/19
 * 消息adapter
 */
public class SystemMessageAdapter extends RecyclerView.Adapter<SystemMessageAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SystemMessageBean> list;

    public SystemMessageAdapter(Context context, ArrayList<SystemMessageBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_system_message, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final SystemMessageAdapter.ViewHolder holder, int position) {
        int type = list.get(position).getOrderType();
        if (type == 1){
            holder.reminder_type.setText("完成资料提醒");
            holder.order_code.setText("您的个人资料未完善，请尽快完善您的个人资料，以便尊享更多特色服务。");
            holder.tv_order_code.setVisibility(View.GONE);
            holder.pay_money.setVisibility(View.GONE);
            holder.tv_pay_money.setVisibility(View.GONE);
            holder.order_time.setVisibility(View.GONE);
            holder.tv_order_time.setVisibility(View.GONE);
            holder.delivery_time.setVisibility(View.GONE);
            holder.tv_delivery_time.setVisibility(View.GONE);
        }else if (type == 2){
            holder.reminder_type.setText("余额变动提醒");
            holder.order_code.setText("订单退款：");
            holder.tv_order_code.setText("￥126.00");
            holder.pay_money.setText("提现账户：");
            holder.tv_pay_money.setText("2018.06.8 20:09");
            holder.order_time.setText("提现状态：");
            holder.tv_order_time.setText("审核中");
            holder.delivery_time.setText("申请日期");
            holder.tv_delivery_time.setText("2018.09.9");
            holder.delivery_time.setVisibility(View.VISIBLE);
            holder.tv_delivery_time.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView reminder_type,tv_order_code,order_code,pay_money,tv_pay_money
                ,order_time,tv_order_time,delivery_time,tv_delivery_time;
        public ViewHolder(View itemView) {
            super(itemView);
            reminder_type = itemView.findViewById(R.id.reminder_type);
            order_code = itemView.findViewById(R.id.order_code);
            tv_order_code = itemView.findViewById(R.id.tv_order_code);
            pay_money = itemView.findViewById(R.id.pay_money);
            tv_pay_money = itemView.findViewById(R.id.tv_pay_money);
            order_time = itemView.findViewById(R.id.order_time);
            tv_order_time = itemView.findViewById(R.id.tv_order_time);
            delivery_time = itemView.findViewById(R.id.delivery_time);
            tv_delivery_time = itemView.findViewById(R.id.tv_delivery_time);
        }

    }

}
