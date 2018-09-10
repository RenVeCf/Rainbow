package com.ipd.taxiu.adapter

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ipd.taxiu.R
import com.ipd.taxiu.utils.trimvideo.TrimVideoUtil
import kotlinx.android.synthetic.main.video_thumb_item_layout.view.*

/**
 * Created by jumpbox on 2017/7/23.
 */

class VideoTrimmerAdapter(private val mContext: Context, private val list: ArrayList<Bitmap>) : RecyclerView.Adapter<VideoTrimmerAdapter.ViewHolder>() {


    fun addBitmap(bitmap: Bitmap) {
        list.add(bitmap)
        notifyDataSetChanged()
    }

    fun clearBitmap(){
        list.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.video_thumb_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        holder.itemView.thumb.setImageBitmap(info)
    }

    override fun getItemCount(): Int = list?.size

    inner class ViewHolder : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView) {
            val layoutParams = itemView.thumb.layoutParams as LinearLayout.LayoutParams
            layoutParams.width = TrimVideoUtil.VIDEO_FRAMES_WIDTH / TrimVideoUtil.MAX_COUNT_RANGE
            itemView.thumb.layoutParams = layoutParams
        }
    }


}
