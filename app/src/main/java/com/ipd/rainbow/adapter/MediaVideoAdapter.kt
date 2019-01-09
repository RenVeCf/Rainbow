package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.VideoShowBean
import com.ipd.rainbow.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_video.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class MediaVideoAdapter(val context: Context, private val list: List<VideoShowBean>?, val itemClick: ((info:VideoShowBean, pos: Int) -> Unit)?) : RecyclerView.Adapter<MediaVideoAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_video, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        ImageLoader.loadNoPlaceHolderImg(context, info.videoCover, holder.itemView.iv_image)
        if (itemClick != null) {
            holder.itemView.setOnClickListener {
                itemClick.invoke(info, position)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}