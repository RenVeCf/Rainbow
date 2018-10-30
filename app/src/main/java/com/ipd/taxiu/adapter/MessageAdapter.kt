package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.MessageBean
import kotlinx.android.synthetic.main.item_message.view.*


class MessageAdapter(private val context: Context, private val list: List<MessageBean>?, private val itemClick: (info: MessageBean) -> Unit) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.tv_message_title.text = info.TITLE
        holder.itemView.tv_message.text = info.CONTENT
        holder.itemView.tv_message_time.text = info.CREATETIME

        holder.itemView.setOnClickListener {
            itemClick?.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
