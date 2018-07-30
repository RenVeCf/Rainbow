package com.ipd.taxiu.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ipd.taxiu.R;
import com.ipd.taxiu.widget.ChooseImageDialog;

import java.util.List;


/**
 * Created by Miss on 2018/7/20
 */
public class EvaluateAdapter extends RecyclerView.Adapter<EvaluateAdapter.ViewHolder> {
    private RecyclerView mRecyclerView;

    private List<String> data;
    private Context mContext;

    private View VIEW_FOOTER;

    //Type
    private int TYPE_NORMAL = 1000;
    private int TYPE_FOOTER = 1002;


    public EvaluateAdapter(List<String> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new ViewHolder(VIEW_FOOTER);
        }  else {
            return new ViewHolder(getLayout(R.layout.item_evaluate));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if ( !isFooterView(position)) {
            holder.choose_goods_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChooseImageDialog dialog = new ChooseImageDialog(mContext,R.style.recharge_pay_dialog,"拍摄图片");
                    dialog.show();
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        int count = (data == null ? 0 : data.size());
        if (VIEW_FOOTER != null) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private View getLayout(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }



    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
            notifyItemInserted(getItemCount() - 1);
        }
    }


    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout choose_goods_pic;
        public ViewHolder(View itemView) {
            super(itemView);
            choose_goods_pic  = itemView.findViewById(R.id.ll_choose_goods_pic);
        }
    }
}
