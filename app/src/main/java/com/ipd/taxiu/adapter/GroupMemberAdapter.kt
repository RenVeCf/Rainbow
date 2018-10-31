package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.UserBean
import com.ipd.taxiu.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_group_member.view.*

/**
Created by Miss on 2018/8/13
 */
class GroupMemberAdapter(val context: Context, val count: Int, private val data: List<UserBean>?, private val itemClick: (info: UserBean?) -> Unit) : RecyclerView.Adapter<GroupMemberAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_group_member, parent, false))
    }

    override fun getItemCount(): Int = count

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var info: UserBean? = null
        if (data?.size ?: 0 > position) {
            info = data?.get(position)
        }
        if (info == null) {
            holder.itemView.user_avatar.setImageResource(R.mipmap.add_image_group)

        } else {
            ImageLoader.loadAvatar(context, info.LOGO, holder.itemView.user_avatar)
        }

        holder.itemView.setOnClickListener {
            itemClick?.invoke(info)
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}