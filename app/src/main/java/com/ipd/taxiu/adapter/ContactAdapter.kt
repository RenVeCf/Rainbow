package com.ipd.taxiu.adapter

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ContactBean
import com.ipd.taxiu.ui.activity.referral.HomepageActivity
import kotlinx.android.synthetic.main.item_contact.view.*

/**
Created by Miss on 2018/8/13
 */
class ContactAdapter(val context: Context, private val data: List<ContactBean>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        when {
            data[position].status == 1 -> {
                holder?.itemView?.done_attention?.visibility = View.VISIBLE
                holder?.itemView?.add_attention?.visibility = View.GONE
                holder?.itemView?.attention?.visibility = View.GONE
            }
            data[position].status == 2 -> {
                holder?.itemView?.done_attention?.visibility = View.GONE
                holder?.itemView?.add_attention?.visibility = View.VISIBLE
                holder?.itemView?.attention?.visibility = View.GONE

                holder?.itemView?.add_attention?.setOnClickListener{
                    holder?.itemView?.add_attention?.visibility = View.GONE
                    holder?.itemView?.attention?.visibility = View.VISIBLE
                }
            }
            else -> {
                holder?.itemView?.done_attention?.visibility = View.GONE
                holder?.itemView?.add_attention?.visibility = View.GONE
                holder?.itemView?.attention?.visibility = View.VISIBLE
            }
        }
        holder?.itemView?.setOnClickListener {
            val intent = Intent(context, HomepageActivity::class.java)
            context.startActivity(intent)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}