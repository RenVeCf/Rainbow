package com.ipd.taxiu.adapter

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.AttentionBean
import com.ipd.taxiu.bean.ContactBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.HttpUrl.IMAGE_URL
import com.ipd.taxiu.presenter.MinePresenter
import com.ipd.taxiu.ui.activity.referral.HomepageActivity
import kotlinx.android.synthetic.main.item_contact.view.*

/**
Created by Miss on 2018/8/13
 */
class ContactAdapter(val context: Context, private val data: List<AttentionBean>, val mPresenter: MinePresenter<MinePresenter.IAttentionView>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        when {
            data[position].IS_ATTEN == 0 -> {
                holder?.itemView?.add_attention?.visibility = View.VISIBLE
                holder?.itemView?.done_attention?.visibility = View.GONE
                holder?.itemView?.attention?.visibility = View.GONE

                onClickListener(holder?.itemView?.add_attention!!, position)
            }

            data[position].IS_ATTEN == 1 -> {
                holder?.itemView?.done_attention?.visibility = View.VISIBLE
                holder?.itemView?.add_attention?.visibility = View.GONE
                holder?.itemView?.attention?.visibility = View.GONE

                onClickListener(holder?.itemView?.done_attention!!, position)
            }

            data[position].IS_ATTEN == 2 -> {
                holder?.itemView?.done_attention?.visibility = View.GONE
                holder?.itemView?.add_attention?.visibility = View.GONE
                holder?.itemView?.attention?.visibility = View.VISIBLE

                onClickListener(holder?.itemView?.attention!!, position)
            }
        }

        ImageLoader.loadAvatar(context, IMAGE_URL + data[position].LOGO, holder?.itemView?.civ_friend_header)
        holder?.itemView?.tv_friend_nickname?.text = data[position].NICKNAME
        holder?.itemView?.tv_create_time?.text = "注册日期：" + CommonUtils.textCut(data[position].CREATETIME, " ")
        holder?.itemView?.setOnClickListener {
            val intent = Intent(context, HomepageActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("AttentionBean", data[position])
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    private fun onClickListener(lin: LinearLayout, position: Int) {
        lin.setOnClickListener {
            mPresenter.attention(data[position].USER_ID)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}