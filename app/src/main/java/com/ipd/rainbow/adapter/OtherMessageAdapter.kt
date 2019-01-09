package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.MessageBean
import com.ipd.rainbow.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_other_message.view.*


class OtherMessageAdapter(private val context: Context, private val list: List<MessageBean>?, private val itemClick: (info: MessageBean) -> Unit) : RecyclerView.Adapter<OtherMessageAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_other_message, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.tv_other_title.text = info.TITLE
        holder.itemView.tv_message_time.text = info.CREATETIME
        ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_message_img)

        holder.itemView.setOnClickListener {
            itemClick?.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
