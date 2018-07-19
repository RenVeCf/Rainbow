package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.TopicCommentBean
import kotlinx.android.synthetic.main.item_topic_comment.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class TopicDetailAdapter(val context: Context, private val list: List<TopicCommentBean>?, private val itemClick: (info: TopicCommentBean) -> Unit) : RecyclerView.Adapter<TopicDetailAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size?.plus(1) ?: 1

    object ItemType {
        const val HEADER = 0
        const val COMMENT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ItemType.HEADER
            else -> ItemType.COMMENT
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.HEADER -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_topic_header, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_topic_comment, parent, false))
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.HEADER -> {
                holder.itemView.setOnClickListener { }
            }
            ItemType.COMMENT -> {
                val info = list!![position - 1]
                if (info.images == null || info.images.isEmpty()) {
                    holder.itemView.image_recycler_view.visibility = View.GONE
                } else {
                    holder.itemView.image_recycler_view.visibility = View.VISIBLE
                    holder.itemView.image_recycler_view.adapter = TopicCommentImgAdapter(context, info.images, {

                    })
                }

                holder.itemView.setOnClickListener {
                    itemClick.invoke(info)

                }
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}