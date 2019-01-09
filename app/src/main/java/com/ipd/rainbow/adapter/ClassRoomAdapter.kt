package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ClassRoomBean
import kotlinx.android.synthetic.main.item_list_classroom.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class ClassRoomAdapter(val context: Context, private val list: List<ClassRoomBean>?, val isBuyed: Boolean, private val itemClick: (info: ClassRoomBean) -> Unit) : RecyclerView.Adapter<ClassRoomAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_classroom, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        holder.itemView.classroom_layout.setData(isBuyed,info)
        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}