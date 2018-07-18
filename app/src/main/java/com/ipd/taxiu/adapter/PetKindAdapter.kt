package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.PetInfoBean
import kotlinx.android.synthetic.main.item_pet_kind.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class PetKindAdapter(val context: Context, private val list: List<PetInfoBean>?, private val itemClick: (info: PetInfoBean) -> Unit) : RecyclerView.Adapter<PetKindAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pet_kind, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.iv_pet.setImageResource(info.res)
        holder.itemView.tv_pet_name.text = info.name

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}