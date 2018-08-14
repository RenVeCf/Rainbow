package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import kotlinx.android.synthetic.main.item_common_problem.view.*

/**
Created by Miss on 2018/8/13
 */
class CommonProblemAdapter(val context: Context,private val data:List<String>): RecyclerView.Adapter<CommonProblemAdapter.ViewHolder>() {
    private var expandPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_common_problem,parent,false))
    }

    override fun getItemCount(): Int = data?.size ?:0

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.itemView?.item_question?.text = data[position]
        holder?.itemView?.tv_detailed_description?.visibility = (if (position == expandPosition) View.VISIBLE else View.GONE)
        holder?.itemView?.iv_status?.setImageResource(if (position == expandPosition) R.mipmap.arrow_up else R.mipmap.arrow_down)
        holder?.itemView?.rl_item?.setOnClickListener({ togglePosition(position) })
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)


    fun togglePosition(position: Int) {
        expandPosition = if (expandPosition != position) {
            notifyItemChanged(expandPosition)
            position
        } else {
            -1
        }
        notifyItemChanged(position)
    }
}