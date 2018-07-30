package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.PetInfoBean
import kotlinx.android.synthetic.main.item_index_title.view.*
import kotlinx.android.synthetic.main.item_pet_kind.view.*
import me.yokeyword.indexablerv.IndexableAdapter

/**
 * Created by jumpbox on 2017/8/31.
 */
class PetKindAdapter(val context: Context, private val itemClick: (info: PetInfoBean) -> Unit) : IndexableAdapter<PetInfoBean>() {
    override fun onCreateTitleViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_index_title, parent, false))
    }

    override fun onCreateContentViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pet_kind, parent, false))
    }


    override fun onBindTitleViewHolder(holder: RecyclerView.ViewHolder, indexTitle: String) {
        holder.itemView.tv_index.text = indexTitle
    }

    override fun onBindContentViewHolder(holder: RecyclerView.ViewHolder, info: PetInfoBean) {
        holder.itemView.iv_pet.setImageResource(info.res)
        holder.itemView.tv_pet_name.text = info.name

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}