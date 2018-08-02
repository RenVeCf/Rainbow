package com.ipd.taxiu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.PictureBean;
import com.ipd.taxiu.ui.activity.PhotoSelectActivity;
import com.ipd.taxiu.widget.PictureRecyclerView;
import com.ipd.taxiu.widget.RatingBar;

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

    private int requestCode, resultCode;
    private Intent datas;

    private int selectPosition = -1;

    public EvaluateAdapter(List<String> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new ViewHolder(VIEW_FOOTER);
        } else {
            return new ViewHolder(getLayout(R.layout.item_evaluate));
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (!isFooterView(position)) {
            holder.choose_goods_pic.initTwo();
            holder.choose_goods_pic.getEvaluateAdapter().setOnClickListener(new PictureEvaluateAdapter.OnClickListener() {
                @Override
                public void onClick() {
                    selectPosition = position;
                }
            });
            if (selectPosition == position) {
                holder.choose_goods_pic.onActivityResult(requestCode, resultCode, datas);
            }
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

             RatingBar rating_star1,rating_star2,rating_star3;
             TextView tv_satisfaction1,tv_satisfaction2,tv_satisfaction3;

            rating_star1 = footerView.findViewById(R.id.rating_star1);
            rating_star2 = footerView.findViewById(R.id.rating_star2);
            rating_star3 = footerView.findViewById(R.id.rating_star3);

            tv_satisfaction1 = footerView.findViewById(R.id.description_satisfaction_degree);
            tv_satisfaction2 = footerView.findViewById(R.id.logistics_satisfaction_degree);
            tv_satisfaction3 = footerView.findViewById(R.id.attitude_satisfaction_degree);

            setStar(rating_star1, tv_satisfaction1);
            setStar(rating_star2, tv_satisfaction2);
            setStar(rating_star3, tv_satisfaction3);
        }
    }


    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        PictureRecyclerView choose_goods_pic;
        LinearLayout linear_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            choose_goods_pic = itemView.findViewById(R.id.picture_recycler_view);
            linear_layout = itemView.findViewById(R.id.linear_layout);
        }
    }

    public void setReset(int requestCode, int resultCode, Intent datas, int selectPosition) {
        this.requestCode = 0;
        this.resultCode = 0;
        this.datas = null;
        this.selectPosition = -1;

        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.datas = datas;
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public int getSelectPosition() {
        return selectPosition;
    }


    //根据星星设置评语
    private void setStar(RatingBar ratingBar, final TextView textView){
        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                int starNum = (int) ratingCount;
                if (starNum == 1){
                    textView.setText("差评");
                }
                if (starNum == 2){
                    textView.setText("一般");
                }
                if (starNum == 3){
                    textView.setText("还行");
                }
                if (starNum == 4){
                    textView.setText("满意");
                }
                if (starNum == 5){
                    textView.setText("非常满意");
                }
            }
        });
    }
}
