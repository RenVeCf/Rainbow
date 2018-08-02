package com.ipd.taxiu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ipd.taxiu.R;
import com.ipd.taxiu.widget.RoundImageView;

import java.util.List;

/**
 * Created by Miss on 2018/7/25
 * 退货图片adapter
 */
public class ReturnPictureAdapter extends RecyclerView.Adapter<ReturnPictureAdapter.ViewHolder> {
    private Context context;
    private List<String> datas;

    public ReturnPictureAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReturnPictureAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_return_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.iv_image.setImageResource(R.mipmap.return_img1);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RoundImageView iv_image;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
        }
    }
}
