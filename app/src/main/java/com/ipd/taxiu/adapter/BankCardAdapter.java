package com.ipd.taxiu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.BankCardBean;
import com.ipd.taxiu.ui.activity.balance.BankCardActivity;
import com.ipd.taxiu.ui.activity.balance.UpdateBankCardActivity;

import java.util.List;

/**
 * Created by Miss on 2018/7/20
 */
public class BankCardAdapter extends RecyclerView.Adapter<BankCardAdapter.ViewHolder> {

    private List<BankCardBean> data;
    private Context mContext;
    private String bankType;

    public BankCardAdapter(List<BankCardBean> data, Context mContext,String bankType) {
        this.data = data;
        this.mContext = mContext;
        this.bankType = bankType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(View.inflate(mContext,R.layout.item_bank_card,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.iv_bank_icon.setImageResource(data.get(position).getIconRes());
        holder.tv_bank_title.setText(data.get(position).getTitle());

        holder.rl_bank_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bankType.equals("添加银行卡")) {
                    Intent intent = new Intent(mContext, UpdateBankCardActivity.class);
                    mContext.startActivity(intent);
                }else if (bankType.equals("选择银行卡")){
                    BankCardActivity activity = (BankCardActivity) mContext;
                    activity.finish();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rl_bank_card;
        ImageView iv_bank_icon;
        TextView tv_bank_title;
        public ViewHolder(View itemView) {
            super(itemView);
            rl_bank_card = itemView.findViewById(R.id.rl_bank_card);
            iv_bank_icon = itemView.findViewById(R.id.iv_bank_icon);
            tv_bank_title = itemView.findViewById(R.id.tv_bank_title);
        }
    }
}
