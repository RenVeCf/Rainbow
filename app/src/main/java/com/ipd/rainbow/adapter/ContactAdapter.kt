package com.ipd.rainbow.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.AttentionBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.ui.activity.referral.HomepageActivity
import com.ipd.rainbow.utils.User
import kotlinx.android.synthetic.main.item_contact.view.*

/**
Created by Miss on 2018/8/13
 */
class ContactAdapter(val context: Context, private val list: List<AttentionBean>?, val itemClick: (position: Int, info: AttentionBean) -> Unit) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false))
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        ImageLoader.loadAvatar(context, info.LOGO, holder.itemView.civ_friend_header)
        holder.itemView.tv_friend_nickname.text = info.NICKNAME
        holder.itemView.tv_create_time.text = "注册日期：" + CommonUtils.textCut(info.CREATETIME, " ")

        setAttent(info.IS_ATTEN, holder.itemView.ll_attention)

        holder.itemView.ll_attention.setOnClickListener {
            itemClick.invoke(position, info)
        }

        holder.itemView.setOnClickListener {
            HomepageActivity.launch(context as Activity, info.USER_ID)
        }
    }


    fun setAttent(isAttent: Int, ll_attent: LinearLayout) {
        User.setAttentBtnStyle(context, isAttent, ll_attent)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}