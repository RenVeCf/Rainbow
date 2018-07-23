package com.ipd.taxiu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.OrderBean;
import com.ipd.taxiu.bean.ReturnBean;
import com.ipd.taxiu.ui.activity.order.OrderDetailActivity;
import com.ipd.taxiu.ui.activity.order.ReturnDetailActivity;
import com.ipd.taxiu.ui.activity.order.ReturnRecordDetailActivity;

import java.util.ArrayList;

/**
 * Created by Miss on 2018/7/19
 * 退款退货adapter
 */
public class ReturnAdapter extends RecyclerView.Adapter<ReturnAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ReturnBean> list;

    public ReturnAdapter(Context context, ArrayList<ReturnBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_return_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ReturnAdapter.ViewHolder holder, int position) {
        String reasonStr = holder.tv_return_reason.getText().toString();
        SpannableString ss = new SpannableString(reasonStr);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#EC601E"));
        ss.setSpan(colorSpan, 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.tv_return_reason.setText(ss);

        final int returnStatus = list.get(position).getReturnStatus();
        final int returnType = list.get(position).getReturnType();
        if (returnType == 1) {
            holder.return_image.setVisibility(View.GONE);
            holder.tv_return_type.setText("退款");
        }
        if (returnType == 2) {
            holder.return_image.setVisibility(View.VISIBLE);
            holder.tv_return_type.setText("退货");
        }
        if (list.get(position).getStatusStr().equals("审核中")) {
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ReturnDetailActivity.class);
                    intent.putExtra("returnStatus", returnStatus);
                    intent.putExtra("returnType", returnType);
                    context.startActivity(intent);
                }
            });
        }
        if (list.get(position).getStatusStr().equals("已通过")) {
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ReturnRecordDetailActivity.class);
                    intent.putExtra("returnStatus", returnStatus);
                    intent.putExtra("returnType", returnType);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_return_reason, tv_return_type;
        RelativeLayout relativeLayout;
        ImageView return_image;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_return_reason = itemView.findViewById(R.id.tv_return_reason);
            relativeLayout = itemView.findViewById(R.id.rl_item);
            return_image = itemView.findViewById(R.id.return_image);
            tv_return_type = itemView.findViewById(R.id.tv_return_type);
        }

    }

}
