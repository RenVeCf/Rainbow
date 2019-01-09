package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.CommentReplyBean
import com.ipd.rainbow.bean.TalkCommentBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.widget.CommentsView
import kotlinx.android.synthetic.main.item_talk_comment.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class TalkAllCommentAdapter(val context: Context, private val isMine: Boolean, private val isSure: Int, private val sortChange: (sortType: Int) -> Unit, private val list: List<TalkCommentBean>?, private val itemClick: (pos: Int, resId: Int, info: TalkCommentBean?, replyInfo: CommentReplyBean?) -> Unit) : RecyclerView.Adapter<TalkAllCommentAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_talk_comment, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        ImageLoader.loadAvatar(context, info.LOGO, holder.itemView.civ_answer_avatar)
        holder.itemView.tv_answer_nickname.text = info.NICKNAME
        holder.itemView.tv_answer_content.text = info.CONTENT
        holder.itemView.tv_answer_time.text = info.CREATETIME
        holder.itemView.tv_answer_zan_num.text = info.PRAISE.toString()
        holder.itemView.iv_answer_zan.isSelected = info.IS_PRAISE == 1
        holder.itemView.tv_answer_zan_num.isSelected = info.IS_PRAISE == 1


        //最佳答案
        if (isMine && isSure == 0) {
            holder.itemView.iv_best_answer.visibility = View.GONE
            holder.itemView.tv_choose_best_answer.visibility = View.VISIBLE
        } else {
            holder.itemView.iv_best_answer.visibility = if (info.STATUS == 2)  /*最佳答案*/ View.VISIBLE else View.GONE
            holder.itemView.tv_choose_best_answer.visibility = View.GONE
        }


        holder.itemView.tv_choose_best_answer.setOnClickListener {
            itemClick.invoke(position, R.id.tv_choose_best_answer, info, null)
        }

        if (info.REPLY_DATA != null) {
            if (info.REPLY_DATA.REPLY_LIST == null || info.REPLY_DATA.REPLY_LIST.isEmpty()) {
                holder.itemView.comments_view.visibility = View.GONE
            } else {
                holder.itemView.comments_view.visibility = View.VISIBLE
                holder.itemView.comments_view.setHasMore(info.REPLY_DATA.SHOW_MORE == 1)
                holder.itemView.comments_view.setList(info.REPLY_DATA.REPLY_LIST)
                holder.itemView.comments_view.notifyDataSetChanged()
                holder.itemView.comments_view.setOnItemClickListener(object : CommentsView.onItemClickListener {
                    override fun onItemClick(position: Int, bean: CommentReplyBean?) {
                        //二级回复
                        itemClick.invoke(position, holder.itemView.comments_view.id, info, bean)
                    }

                })
            }
        }

        holder.itemView.ll_answer_zan.setOnClickListener {
            //点赞
            itemClick.invoke(position, it.id, info, null)
        }

        holder.itemView.setOnClickListener {
            //一级回复
            itemClick.invoke(position, -1, info, null)
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}