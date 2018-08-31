package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.TalkBean
import com.ipd.taxiu.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_talk.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class TalkAdapter(val context: Context, private val list: List<TalkBean>?, private val itemClick: (info: TalkBean) -> Unit) : RecyclerView.Adapter<TalkAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_talk, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        ImageLoader.loadAvatar(context, info.User.LOGO, holder.itemView.civ_publisher_avatar)
        holder.itemView.tv_publisher_nickname.text = info.User.NICKNAME
        holder.itemView.tv_title.text = info.CONTENT
        holder.itemView.tv_award_integral.text = info.SCORE.toString()
        holder.itemView.tv_publish_time.text = info.CREATETIME
        holder.itemView.tv_viewers_num.text = info.BROWSE.toString()
        holder.itemView.tv_comment_num.text = info.REPLY.toString()
        holder.itemView.tv_collect_num.text = info.COLLECT.toString()
        holder.itemView.ll_award.visibility = if (info.SCORE == 0) View.GONE else View.VISIBLE

        if (info.ANSWER_DATA == null || info.ANSWER_DATA.USER_ID == 0) {
            holder.itemView.view_line.visibility = View.GONE
            holder.itemView.cl_answer.visibility = View.GONE
        } else {
            holder.itemView.view_line.visibility = View.VISIBLE
            holder.itemView.cl_answer.visibility = View.VISIBLE
            ImageLoader.loadAvatar(context, info.ANSWER_DATA.LOGO, holder.itemView.civ_answer_avatar)
            holder.itemView.tv_answer_nickname.text = info.ANSWER_DATA.NICKNAME
            holder.itemView.tv_answer_content.text = info.ANSWER_DATA.CONTENT
            holder.itemView.tv_answer_time.text = info.ANSWER_DATA.CREATETIME
            holder.itemView.tv_answer_zan_num.text = info.ANSWER_DATA.PRAISE.toString()
        }


        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}