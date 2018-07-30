package com.ipd.taxiu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.ProductCategoryParentBean;

import java.util.List;


/**
 * Created by jumpbox on 2017/2/7.
 */

public class ParentCategoryAdapter extends RecyclerView.Adapter<ParentCategoryAdapter.ViewHolder> {
    protected Context mContext;
    protected List<ProductCategoryParentBean> list;
    protected LayoutInflater mInflater;
    protected int mCurCheckedPosition = 0;

    public ParentCategoryAdapter(Context mContext, List<ProductCategoryParentBean> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_parent_category, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        setCheckedPosition(position == mCurCheckedPosition, holder);
        holder.tv_parent_category.setText(list.get(position).name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurCheckedPosition == position) {
                    return;
                }
                notifyItemChanged(mCurCheckedPosition);
                mCurCheckedPosition = position;
                notifyItemChanged(mCurCheckedPosition);
                if (mOnItemCheckedListener != null) {
                    mOnItemCheckedListener.onChecked(position);
                }


            }
        });
    }

    private OnItemCheckedListener mOnItemCheckedListener;

    public void setItemCheckedListener(OnItemCheckedListener onItemCheckedListener) {
        mOnItemCheckedListener = onItemCheckedListener;
    }

    private void setCheckedPosition(boolean isChecked, ViewHolder holder) {
        holder.itemView.setSelected(isChecked);
        holder.tv_parent_category.setSelected(isChecked);
        holder.iv_caregory_ischecked.setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }

    public String getCurCheckedCategoryId() {
        return list.get(mCurCheckedPosition).id + "";
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_parent_category;
        ImageView iv_caregory_ischecked;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_parent_category = itemView.findViewById(R.id.tv_parent_category);
            iv_caregory_ischecked = itemView.findViewById(R.id.iv_caregory_ischecked);
        }
    }

    public interface OnItemCheckedListener {
        void onChecked(int position);
    }

}
