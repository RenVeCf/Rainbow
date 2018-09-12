package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jzvd.Jzvd
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.VideoShowBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.http.HttpUrl
import kotlinx.android.synthetic.main.item_video_play.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class MediaVideoPlayAdapter(val context: Context, private val list: List<VideoShowBean>?, val itemClick: ((info: VideoShowBean, pos: Int) -> Unit)?) : RecyclerView.Adapter<MediaVideoPlayAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_video_play, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        ImageLoader.loadNoPlaceHolderImg(context, info.videoCover, holder.itemView.video_player.thumbImageView)
        holder.itemView.video_player.setUp(
                HttpUrl.VIDEO_URL + info.videoUrl,
                "", Jzvd.SCREEN_WINDOW_LIST)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}