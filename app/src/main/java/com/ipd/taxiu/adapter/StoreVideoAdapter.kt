package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.StoreVideoBean
import com.ipd.taxiu.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_list_store_video.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class StoreVideoAdapter(val context: Context, private val list: List<StoreVideoBean>?, private val itemClick: (info: StoreVideoBean) -> Unit) : RecyclerView.Adapter<StoreVideoAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_store_video, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_image)
        holder.itemView.tv_taxiu_name.text = info.TITLE
        holder.itemView.tv_video_viewers.text = info.BROWSE.toString()
        holder.itemView.tv_video_time.text = info.TIME_LENGTH

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}