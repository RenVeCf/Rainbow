package com.ipd.taxiu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom;
import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.OrderDetailBean;

import java.util.Dictionary;
import java.util.List;

import static com.ipd.taxiu.adapter.OrderListAdapter.DELIVERY;
import static com.ipd.taxiu.adapter.OrderListAdapter.DETAIL;
import static com.ipd.taxiu.adapter.OrderListAdapter.EVALUATE;
import static com.ipd.taxiu.adapter.OrderListAdapter.PAYMENT;

/**
 * Created by Miss on 2018/7/20
 */
public class CommonProblemAdapter extends RecyclerView.Adapter<CommonProblemAdapter.ViewHolder> {
    private List<String> data;
    private Context mContext;

    int expandPosition = -1;

    public CommonProblemAdapter(List<String> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getLayout(R.layout.item_common_problem));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.item_question.setText(data.get(position));
        holder.tv_detailed_description.setVisibility(position == expandPosition ? View.VISIBLE : View.GONE);
        holder.iv_status.setImageResource(position == expandPosition ? R.mipmap.arrow_up : R.mipmap.arrow_down);
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePosition(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    private View getLayout(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_question, tv_detailed_description;
        ImageView iv_status;
        RelativeLayout rl_item;

        public ViewHolder(View itemView) {
            super(itemView);
            item_question = itemView.findViewById(R.id.item_question);
            tv_detailed_description = itemView.findViewById(R.id.tv_detailed_description);
            iv_status = itemView.findViewById(R.id.iv_status);
            rl_item = itemView.findViewById(R.id.rl_item);
        }
    }

    public void togglePosition(int position) {
        if (expandPosition != position) {
            notifyItemChanged(expandPosition);
            expandPosition = position;
        } else {
            expandPosition = -1;
        }
        notifyItemChanged(position);
    }
}
