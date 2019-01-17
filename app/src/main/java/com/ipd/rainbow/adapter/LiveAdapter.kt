package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R

/**
 * Created by jumpbox on 2017/8/31.
 */
class LiveAdapter(val context: Context, private val list: List<Any>?, private val itemClick: (info: Any) -> Unit) : RecyclerView.Adapter<LiveAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_live, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}