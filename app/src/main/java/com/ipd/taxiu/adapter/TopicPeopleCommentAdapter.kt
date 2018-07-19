package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.CommentReplyBean
import com.ipd.taxiu.bean.TopicCommentReplyBean
import com.ipd.taxiu.ui.activity.topic.MoreCommentActivity
import com.ipd.taxiu.widget.CommentsView
import kotlinx.android.synthetic.main.item_topic_comment.view.*
import kotlinx.android.synthetic.main.item_topic_people_comment_reply.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class TopicPeopleCommentAdapter(val context: Context, private val list: List<TopicCommentReplyBean>?, private val itemClick: (info: TopicCommentReplyBean) -> Unit) : RecyclerView.Adapter<TopicPeopleCommentAdapter.ViewHolder>() {

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
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_topic_people_comment_header, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_topic_people_comment_reply, parent, false))
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.HEADER -> {
                holder.itemView.image_recycler_view.visibility = View.VISIBLE
                holder.itemView.image_recycler_view.adapter = TopicCommentImgAdapter(context, arrayListOf(R.mipmap.topic_detail_image), {

                })

                holder.itemView.setOnClickListener { }
            }
            ItemType.COMMENT -> {
                val info = list!![position - 1]
                if (info.subCommentList == null || info.subCommentList.isEmpty()) {
                    holder.itemView.comments_view.visibility = View.GONE
                } else {
                    holder.itemView.comments_view.visibility = View.VISIBLE
                    holder.itemView.comments_view.setMaxShowNum(5)
                    holder.itemView.comments_view.setList(info.subCommentList)
                    holder.itemView.comments_view.notifyDataSetChanged()
                    holder.itemView.comments_view.setOnItemClickListener(object : CommentsView.onItemClickListener {
                        override fun onItemClick(position: Int, bean: CommentReplyBean?) {
                            if (bean == null) {
                                //更多回复
                                MoreCommentActivity.launch(context as Activity)
                            } else {

                            }

                        }

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