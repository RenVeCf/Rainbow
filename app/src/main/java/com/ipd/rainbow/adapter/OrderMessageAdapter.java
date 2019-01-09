package com.ipd.rainbow.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ipd.rainbow.R;
import com.ipd.rainbow.bean.OrderMessageBean;

import java.util.ArrayList;

/**
 * Created by Miss on 2018/7/19
 * 消息adapter
 */
public class OrderMessageAdapter extends RecyclerView.Adapter<OrderMessageAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderMessageBean> list;

    public OrderMessageAdapter(Context context, ArrayList<OrderMessageBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_message, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final OrderMessageAdapter.ViewHolder holder, int position) {
        int status = list.get(position).getOrderStatus();
        if (status == 1){
            holder.reminder_payment.setText("待付款提醒");
            holder.pay_money.setText("订单金额：");
            holder.tv_pay_money.setText("￥12.50");
            holder.order_time.setText("创建时间：");
            holder.tv_order_time.setText("2018.06.8 20:09");
            holder.delivery_time.setVisibility(View.GONE);
            holder.tv_delivery_time.setVisibility(View.GONE);
            holder.tracking_number.setVisibility(View.GONE);
            holder.tv_tracking_number.setVisibility(View.GONE);
        }else if (status == 2){
            holder.reminder_payment.setText("待发货提醒");
            holder.pay_money.setText("订单状态：");
            holder.tv_pay_money.setText("已发货");
            holder.order_time.setText("发货时间：");
            holder.tv_order_time.setText("2018.06.8 20:09");
            holder.delivery_time.setText("快递公司：");
            holder.tv_delivery_time.setText("顺丰");
            holder.tracking_number.setText("快递单号：");
            holder.tv_tracking_number.setText("7867878686786");
            holder.delivery_time.setVisibility(View.VISIBLE);
            holder.tv_delivery_time.setVisibility(View.VISIBLE);
            holder.tracking_number.setVisibility(View.VISIBLE);
            holder.tv_tracking_number.setVisibility(View.VISIBLE);
        }else if (status == 3){
            holder.reminder_payment.setText("待收货提醒");
            holder.pay_money.setText("订单状态：");
            holder.tv_pay_money.setText("待收货");
            holder.order_time.setText("发货时间：");
            holder.tv_order_time.setText("2018.06.8 20:09");
            holder.delivery_time.setVisibility(View.GONE);
            holder.tv_delivery_time.setVisibility(View.GONE);
            holder.tracking_number.setVisibility(View.GONE);
            holder.tv_tracking_number.setVisibility(View.GONE);
        }else if (status == 4){
            holder.reminder_payment.setText("订单完成提醒");
            holder.pay_money.setText("订单状态：");
            holder.tv_pay_money.setText("已完成");
            holder.order_time.setText("收货时间：");
            holder.tv_order_time.setText("2018.06.8 20:09");
            holder.delivery_time.setVisibility(View.GONE);
            holder.tv_delivery_time.setVisibility(View.GONE);
            holder.tracking_number.setVisibility(View.GONE);
            holder.tv_tracking_number.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView reminder_payment,order_code,pay_money,tv_pay_money,order_time,tv_order_time,
                delivery_time,tv_delivery_time,tracking_number,tv_tracking_number;
        public ViewHolder(View itemView) {
            super(itemView);
            reminder_payment = itemView.findViewById(R.id.reminder_payment);
            order_code = itemView.findViewById(R.id.order_code);
            pay_money = itemView.findViewById(R.id.pay_money);
            tv_pay_money = itemView.findViewById(R.id.tv_pay_money);
            order_time = itemView.findViewById(R.id.order_time);
            tv_order_time = itemView.findViewById(R.id.tv_order_time);
            delivery_time = itemView.findViewById(R.id.delivery_time);
            tv_delivery_time = itemView.findViewById(R.id.tv_delivery_time);
            tracking_number = itemView.findViewById(R.id.tracking_number);
            tv_tracking_number = itemView.findViewById(R.id.tv_tracking_number);
        }

    }

}
