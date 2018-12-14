package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.TaxiuCommentBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.ui.activity.PictureLookActivity
import com.ipd.taxiu.utils.StringUtils
import kotlinx.android.synthetic.main.item_topic_comment.view.*


/**
 * Created by jumpbox on 2017/8/31.
 */
class TaxiuAllCommentAdapter(val context: Context, private val sortChange: (sortType: Int) -> Unit, private val list: List<TaxiuCommentBean>?, private val itemClick: (pos: Int, resId: Int, info: TaxiuCommentBean?) -> Unit) : RecyclerView.Adapter<TaxiuAllCommentAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TaxiuAllCommentAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_topic_comment, parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        ImageLoader.loadAvatar(context, info.LOGO, holder.itemView.civ_commenter_avatar)
        holder.itemView.tv_commenter_name.text = info.NICKNAME
        holder.itemView.tv_answer_content.text = info.CONTENT
        holder.itemView.tv_comment_time.text = info.CREATETIME
        holder.itemView.tv_comment_viewers_num.text = info.BROWSE.toString()
        holder.itemView.tv_comment_reply_num.text = info.REPLY.toString()
        holder.itemView.tv_comment_zan_num.text = info.PRAISE.toString()
        holder.itemView.iv_comment_zan.isSelected = info.IS_PRAISE == 1
        holder.itemView.tv_comment_zan_num.isSelected = info.IS_PRAISE == 1

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


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}