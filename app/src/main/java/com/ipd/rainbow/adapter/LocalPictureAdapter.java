package com.ipd.rainbow.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ipd.rainbow.R;
import com.ipd.rainbow.bean.LocalPictureBean;
import com.ipd.rainbow.imageload.ImageLoader;
import com.ipd.rainbow.utils.BaseAdapter;

import java.util.HashMap;
import java.util.List;


/**
 * Created by jumpbox on 2017/7/23.
 */

public class LocalPictureAdapter extends BaseAdapter<LocalPictureAdapter.ViewHolder> {

    private Context mContext;
    private List<LocalPictureBean> list;
    private HashMap<String, LocalPictureBean> mCheckedPictureMap;
    private int maxSize;

    public LocalPictureAdapter(Context context, List<LocalPictureBean> list, int maxSize) {
        mContext = context;
        this.list = list;
        mCheckedPictureMap = new HashMap<>();
        this.maxSize = maxSize;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_local_picture, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final LocalPictureBean info = list.get(position);
        if (position == 0) {
            Glide.with(mContext).load(R.mipmap.capture).into(holder.iv_local_picture);
            holder.cb_select.setVisibility(View.GONE);
        } else {
            holder.cb_select.setVisibility(View.VISIBLE);

            ImageLoader.loadImgFromLocal(mContext, list.get(position).path, holder.iv_local_picture);
            if (mCheckedPictureMap.containsKey(info.path)) {
                holder.cb_select.setChecked(true);
            } else {
                holder.cb_select.setChecked(false);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    //拍摄照片
                    if (mOnTakePhotoListener != null) mOnTakePhotoListener.onTakePhoto();
                    return;
                }


                if (mCheckedPictureMap.containsKey(info.path)) {
                    mCheckedPictureMap.remove(info.path);
                } else {
                    if (mCheckedPictureMap.size() == maxSize) {
                        return;
                    }

                    mCheckedPictureMap.put(info.path, info);
                }

                if (mOnItemSelectChangeListener != null) {
                    mOnItemSelectChangeListener.onChange(mCheckedPictureMap.size());
                }
                notifyItemChanged(position);
            }
        });


    }

    private OnItemSelectChangeListener mOnItemSelectChangeListener;

    public void setOnItemSelectChangeListener(OnItemSelectChangeListener onItemSelectChangeListener) {
        mOnItemSelectChangeListener = onItemSelectChangeListener;
    }

    private OnTakePhotoListener mOnTakePhotoListener;

    public void setOnTakePhotoListener(OnTakePhotoListener onTakePhotoListener) {
        mOnTakePhotoListener = onTakePhotoListener;
    }

    public HashMap<String, LocalPictureBean> getCheckedPictureMap() {
        return mCheckedPictureMap;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_local_picture;
        CheckBox cb_select;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_local_picture = itemView.findViewById(R.id.iv_local_picture);
            cb_select = itemView.findViewById(R.id.cb_select);
        }
    }

    public interface OnItemSelectChangeListener {
        void onChange(int size);
    }

    public interface OnTakePhotoListener {
        void onTakePhoto();
    }

}
