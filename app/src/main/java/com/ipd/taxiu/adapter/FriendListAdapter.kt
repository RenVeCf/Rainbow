package com.ipd.taxiu.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.FriendBean
import com.ipd.taxiu.ui.activity.referral.HomepageActivity

/**
Created by Miss on 2018/8/13
 */
class FriendListAdapter(val context: Context, private val data: List<FriendBean>) : RecyclerView.Adapter<FriendListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_friend_list, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?: 0
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.itemView?.setOnClickListener {
            val intent = Intent(context, HomepageActivity::class.java)
            context.startActivity(intent)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}