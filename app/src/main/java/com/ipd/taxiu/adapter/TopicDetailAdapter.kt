package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.TopicCommentBean
import com.ipd.taxiu.bean.TopicDetailBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.ui.activity.PictureLookActivity
import com.ipd.taxiu.utils.StringUtils
import kotlinx.android.synthetic.main.item_topic_comment.view.*
import kotlinx.android.synthetic.main.layout_topic_header.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class TopicDetailAdapter(val context: Context, private val detailData: TopicDetailBean, private val list: List<TopicCommentBean>?, private val itemClick: (pos: Int, resId: Int, info: TopicCommentBean?) -> Unit) : RecyclerView.Adapter<TopicDetailAdapter.ViewHolder>() {

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
                if (!TextUtils.isEmpty(detailData.LOGO)) {
                    holder.itemView.iv_topic_image.visibility = View.VISIBLE
                    ImageLoader.loadNoPlaceHolderImg(context, detailData.LOGO, holder.itemView.iv_topic_image)
                } else {
                    holder.itemView.iv_topic_image.visibility = View.GONE
                }
                holder.itemView.tv_topic_title.text = detailData.TITLE
                holder.itemView.tv_topic_desc.text = detailData.CONTENT
                holder.itemView.tv_taxiu_publish_time.text = detailData.CREATETIME
                holder.itemView.tv_viewers_num.text = detailData.BROWSE.toString()
                holder.itemView.iv_zan.isSelected = detailData.IS_PRAISE == 1
                holder.itemView.tv_zan.text = detailData.PRAISE.toString()
                holder.itemView.tv_comment_join_num.text = "${detailData.COMMENT_NUM}人参与了该话题的讨论"


                holder.itemView.iv_zan.setOnClickListener {
                    itemClick.invoke(position, holder.itemView.iv_zan.id, null)
                }
            }
            ItemType.COMMENT -> {
                val info = list!![position - 1]
                ImageLoader.loadAvatar(context, info.LOGO, holder.itemView.civ_commenter_avatar)
                holder.itemView.tv_commenter_name.text = info.NICKNAME
                holder.itemView.tv_answer_content.text = info.CONTENT
                holder.itemView.tv_comment_time.text = info.CREATETIME
                holder.itemView.tv_comment_viewers_num.text = info.BROWSE.toString()
                holder.itemView.tv_comment_reply_num.text = info.REPLY.toString()
                holder.itemView.tv_comment_zan_num.text = info.PRAISE.toString()
                holder.itemView.iv_comment_zan.isSelected = info.IS_PRAISE == 1


                val pics = StringUtils.splitImages(info.PIC)
                if (pics.isNotEmpty()) {
                    holder.itemView.image_media_view.visibility = View.VISIBLE
                    holder.itemView.image_media_view.image_media_view.adapter = MediaPictureAdapter(context, pics, { list, pos ->
                        PictureLookActivity.launch(context as Activity?, ArrayList(list), pos, PictureLookActivity.URL)
                    })
                } else {
                    holder.itemView.image_media_view.visibility = View.GONE
                }

                holder.itemView.setOnClickListener {
                    itemClick.invoke(position, holder.itemView.id, info)

                }

                holder.itemView.ll_comment_zan.setOnClickListener {
                    itemClick.invoke(position, holder.itemView.ll_comment_zan.id, info)
                }
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}