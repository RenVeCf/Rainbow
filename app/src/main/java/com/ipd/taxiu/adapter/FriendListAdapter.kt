package com.ipd.taxiu.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.AttentionBean
import com.ipd.taxiu.bean.FriendBean
import com.ipd.taxiu.bean.UserBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.http.HttpUrl.IMAGE_URL
import com.ipd.taxiu.ui.activity.referral.HomepageActivity
import kotlinx.android.synthetic.main.item_friend_list.view.*

/**
Created by Miss on 2018/8/13
 */
class FriendListAdapter(val context: Context, private val data: List<AttentionBean>) : RecyclerView.Adapter<FriendListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_friend_list, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?: 0
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        ImageLoader.loadAvatar(context, IMAGE_URL+data[position].LOGO, holder?.itemView?.civ_friend_header)
        holder?.itemView?.tv_friend_nickname?.text = data[position].NICKNAME
        holder?.itemView?.tv_create_time?.text = "注册日期：" + data[position].CREATETIME
        holder?.itemView?.setOnClickListener {
            val intent = Intent(context, HomepageActivity::class.java)
            val bundle= Bundle()
            bundle.putSerializable("AttentionBean",data[position])
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}