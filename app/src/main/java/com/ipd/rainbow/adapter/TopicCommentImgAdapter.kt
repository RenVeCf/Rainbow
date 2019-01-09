package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import kotlinx.android.synthetic.main.item_topic_comment_img.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class TopicCommentImgAdapter(val context: Context, private val list: List<Int>?, private val itemClick: (info: Int) -> Unit) : RecyclerView.Adapter<TopicCommentImgAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_topic_comment_img, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        holder.itemView.iv_topic_comment_image.setImageResource(info)
        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}