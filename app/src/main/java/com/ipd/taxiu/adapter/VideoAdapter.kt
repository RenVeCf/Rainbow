package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.VideoInfo
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.utils.trimvideo.TrimVideoUtil
import kotlinx.android.synthetic.main.item_local_video.view.*

/**
 * Created by jumpbox on 2017/7/23.
 */

class VideoAdapter(private val mContext: Context, private val list: List<VideoInfo>?, val itemClick: (pos: Int, info: VideoInfo) -> Unit) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_local_video, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        ImageLoader.loadImgFromLocal(mContext, TrimVideoUtil.getVideoFilePath(info.videoPath), holder.itemView.iv_image)
        holder.itemView.tv_duration.text = TrimVideoUtil.convertSecondsToTime(info.duration / 1000)

        holder.itemView.setOnClickListener {
            itemClick?.invoke(position, info)
        }

    }

    override fun getItemCount(): Int = list?.size ?: 0

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}
