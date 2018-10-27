package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.CommentDetailBean
import com.ipd.taxiu.bean.CommentReplyBean
import com.ipd.taxiu.bean.TopicCommentReplyBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.ui.activity.PictureLookActivity
import com.ipd.taxiu.utils.ReplyType
import com.ipd.taxiu.utils.StringUtils
import com.ipd.taxiu.utils.User
import com.ipd.taxiu.widget.CommentsView
import kotlinx.android.synthetic.main.item_topic_people_comment_reply.view.*
import kotlinx.android.synthetic.main.layout_topic_people_comment_header.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class TopicPeopleCommentAdapter(val context: Context, val detailData: CommentDetailBean, private val list: List<TopicCommentReplyBean>?, private val itemClick: (pos: Int, replyType: Int, replyId: Int, info: TopicCommentReplyBean?, replyInfo: CommentReplyBean?) -> Unit) : RecyclerView.Adapter<TopicPeopleCommentAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size?.plus(1) ?: 1

    object ItemType {
        const val HEADER = 0
        const val COMMENT = 1
    }

    fun getItemInfo(position: Int): TopicCommentReplyBean? {
        return list?.get(position - 1)
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
                ImageLoader.loadAvatar(context, detailData.User.LOGO, holder.itemView.civ_publisher_avatar)
                holder.itemView.tv_publisher_nickname.text = detailData.User.NICKNAME
                holder.itemView.tv_user_desc.text = detailData.User.TAG
                holder.itemView.tv_comment_content.text = detailData.CONTENT
                holder.itemView.tv_comment_time.text = detailData.CREATETIME
                holder.itemView.tv_comment_viewers_num.text = detailData.BROWSE.toString()
                holder.itemView.tv_comment_zan_num.text = detailData.PRAISE.toString()
                holder.itemView.iv_comment_zan.isSelected = detailData.IS_PRAISE == 1

                val pics = StringUtils.splitImages(detailData.PIC)
                if (pics.isNotEmpty()) {
                    holder.itemView.image_recycler_view.visibility = View.VISIBLE
                    holder.itemView.image_recycler_view.adapter = MediaPictureAdapter(context, pics, { list, pos ->
                        PictureLookActivity.launch(context as Activity?, ArrayList(list), pos, PictureLookActivity.URL)
                    })
                } else {
                    holder.itemView.image_recycler_view.visibility = View.GONE
                }

                holder.itemView.tv_comment_reply_num.text = "回复  ${detailData.REPLY}"


                //点赞
                holder.itemView.ll_comment_zan.setOnClickListener {
                    itemClick.invoke(position, ReplyType.PRAISE_COMMENT, detailData.COMMENT_ID, null, null)
                }

                //关注
                setAttent(position, holder.itemView.ll_attent)

            }
            ItemType.COMMENT -> {
                val info = list!![position - 1]
                ImageLoader.loadAvatar(context, info.LOGO, holder.itemView.civ_sub_publisher_avatar)
                holder.itemView.tv_nickname.text = info.NICKNAME
                holder.itemView.tv_answer_content.text = info.CONTENT
                holder.itemView.tv_sub_comment_time.text = info.CREATETIME
                holder.itemView.tv_sub_comment_viewers_num.text = info.BROWSE.toString()
                holder.itemView.tv_sub_comment_zan_num.text = info.PRAISE.toString()
                holder.itemView.iv_sub_comment_zan.isSelected = info.IS_PRAISE == 1

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
                                if (bean == null) {
                                    //更多回复
                                    itemClick.invoke(position, ReplyType.MORE_REPLY, info.REPLY_ID, null, null)
                                } else {
                                    //二级回复
                                    itemClick.invoke(position, ReplyType.SECOND_REPLY, bean.PARENT, info, bean)
                                }

                            }

                        })
                    }
                }

                holder.itemView.setOnClickListener {
                    //更多回复
                    itemClick.invoke(position, ReplyType.MORE_REPLY, info.REPLY_ID, null, null)
                }

                holder.itemView.ll_sub_comment_zan.setOnClickListener {
                    //回复点赞
                    itemClick.invoke(position, ReplyType.PRAISE_REPLY, info.REPLY_ID, info, null)
                }
            }
        }
    }

    fun setAttent(position: Int, ll_attent: LinearLayout) {
        User.setAttentBtnStyle(context, detailData.User.IS_ATTEN, ll_attent)

        ll_attent.setOnClickListener {
            itemClick.invoke(position, ReplyType.ATTENTION, detailData.User.USER_ID, null, null)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}