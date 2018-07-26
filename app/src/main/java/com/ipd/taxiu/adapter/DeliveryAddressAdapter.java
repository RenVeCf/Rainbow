package com.ipd.taxiu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.activity.address.AddAddressActivity;

import java.util.List;

/**
 * Created by Miss on 2018/7/25
 * 收货地址adapter
 */
public class DeliveryAddressAdapter extends RecyclerView.Adapter<DeliveryAddressAdapter.ViewHolder> {
    private Context context;
    private List<String> datas;

    public DeliveryAddressAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeliveryAddressAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_delivery_address, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0){
            holder.tv_default.setVisibility(View.VISIBLE);
        }
        holder.iv_edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddAddressActivity.class);
                intent.putExtra("addressType","修改地址");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_default;
        ImageView iv_edit_address;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_default = itemView.findViewById(R.id.tv_default);
            iv_edit_address = itemView.findViewById(R.id.iv_edit_address);
        }
    }
}
