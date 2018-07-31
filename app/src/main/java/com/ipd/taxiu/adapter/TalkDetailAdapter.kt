package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.CommentReplyBean
import com.ipd.taxiu.bean.TalkCommentBean
import com.ipd.taxiu.widget.CommentsView
import com.ipd.taxiu.widget.MessageDialog
import kotlinx.android.synthetic.main.item_talk_comment.view.*
import kotlinx.android.synthetic.main.layout_talk_header.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class TalkDetailAdapter(val context: Context, private val isMine: Boolean, private val list: List<TalkCommentBean>?, private val itemClick: (res: Int, info: TalkCommentBean) -> Unit) : RecyclerView.Adapter<TalkDetailAdapter.ViewHolder>() {

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
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_talk_header, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_talk_comment, parent, false))
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.HEADER -> {
                holder.itemView.layout_post_user.visibility = if (isMine) View.GONE else View.VISIBLE
                holder.itemView.cl_share.visibility = if (isMine) View.GONE else View.VISIBLE

                holder.itemView.setOnClickListener { }
            }
            ItemType.COMMENT -> {
                val info = list!![position - 1]

                holder.itemView.iv_best_answer.visibility = if (isMine) View.GONE else View.VISIBLE
                holder.itemView.tv_choose_best_answer.visibility = if (isMine) View.VISIBLE else View.GONE
                holder.itemView.tv_choose_best_answer.setOnClickListener {
                    itemClick.invoke(R.id.tv_choose_best_answer, info)
                }

                if (info.replyList == null || info.replyList.isEmpty()) {
                    holder.itemView.comments_view.visibility = View.GONE
                } else {
                    holder.itemView.comments_view.visibility = View.VISIBLE
                    holder.itemView.comments_view.setList(info.replyList)
                    holder.itemView.comments_view.notifyDataSetChanged()
                    holder.itemView.comments_view.setOnItemClickListener(object : CommentsView.onItemClickListener {
                        override fun onItemClick(position: Int, bean: CommentReplyBean?) {


                        }

                    })
                }


                holder.itemView.setOnClickListener {
                    itemClick.invoke(-1, info)

                }
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}