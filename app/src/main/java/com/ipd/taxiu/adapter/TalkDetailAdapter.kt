package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.CommentReplyBean
import com.ipd.taxiu.bean.TalkCommentBean
import com.ipd.taxiu.bean.TalkDetailBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.widget.CommentsView
import kotlinx.android.synthetic.main.item_talk_comment.view.*
import kotlinx.android.synthetic.main.layout_post_user.view.*
import kotlinx.android.synthetic.main.layout_talk_header.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class TalkDetailAdapter(val context: Context, private val isMine: Boolean, private val detailData: TalkDetailBean, private val list: List<TalkCommentBean>?, private val itemClick: (pos: Int, resId: Int, info: TalkCommentBean?, replyInfo: CommentReplyBean?) -> Unit) : RecyclerView.Adapter<TalkDetailAdapter.ViewHolder>() {

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

                if (!isMine) {
                    ImageLoader.loadAvatar(context, detailData.User.LOGO, holder.itemView.civ_publisher_avatar)
                    holder.itemView.tv_nickname.text = detailData.User.NICKNAME
                    holder.itemView.tv_user_desc.text = detailData.User.TAG
                }

                holder.itemView.tv_publisher_content.text = detailData.CONTENT
                holder.itemView.ll_award.visibility = if (detailData.SCORE == 0) View.GONE else View.VISIBLE
                holder.itemView.tv_award_integral.text = detailData.SCORE.toString()
                holder.itemView.tv_publish_time.text = detailData.CREATETIME
                holder.itemView.tv_viewers_num.text = detailData.BROWSE.toString()

                holder.itemView.iv_zan.isSelected = detailData.IS_PRAISE == 1
                holder.itemView.tv_zan.text = detailData.PRAISE.toString()

                holder.itemView.iv_zan.setOnClickListener {
                    itemClick.invoke(position, it.id, null, null)
                }

                holder.itemView.tv_comment_join_num.text = "${detailData.COMMENT_NUM} 人给出了答案"


            }
            ItemType.COMMENT -> {
                val info = list!![position - 1]

                ImageLoader.loadAvatar(context, info.LOGO, holder.itemView.civ_answer_avatar)
                holder.itemView.tv_answer_nickname.text = info.NICKNAME
                holder.itemView.tv_answer_content.text = info.CONTENT
                holder.itemView.tv_answer_time.text = info.CREATETIME
                holder.itemView.tv_answer_zan_num.text = info.PRAISE.toString()
                holder.itemView.iv_answer_zan.isSelected = info.IS_PRAISE == 1


                //最佳答案
                if (isMine && detailData.IS_SURE == 0) {
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
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}