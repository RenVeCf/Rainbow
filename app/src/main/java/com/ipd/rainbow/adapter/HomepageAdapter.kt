package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.HomepageBean
import kotlinx.android.synthetic.main.item_friend_list1.view.*
import kotlinx.android.synthetic.main.item_friend_list2.view.*

/**
Created by Miss on 2018/8/13
 */
class HomepageAdapter(val context: Context, private val data: List<HomepageBean>) : RecyclerView.Adapter<HomepageAdapter.ViewHolder>() {
    enum class ITEM_TYPE {
        ITEM1,
        ITEM2
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> return ITEM_TYPE.ITEM1.ordinal
            1 -> return ITEM_TYPE.ITEM1.ordinal
        }
        return ITEM_TYPE.ITEM2.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return if (viewType == ITEM_TYPE.ITEM1.ordinal) {
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_friend_list1, parent, false))
        } else {
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_friend_list2, parent, false))
        }
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (position == ITEM_TYPE.ITEM1.ordinal) {
            if (holder?.itemView?.item1 == null) return
            holder?.itemView?.item1?.setOnClickListener {
//               TaxiuDetailActivity.launch(context as Activity,1,false)
            }
        } else {
            if (holder?.itemView?.item2 == null) return
            holder?.itemView?.item2?.setOnClickListener {
//                TaxiuDetailActivity.launch(context as Activity,1,false)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}