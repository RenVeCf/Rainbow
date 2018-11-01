package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.PetBibleBean
import com.ipd.taxiu.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_pet_bible.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class PetBibleAdapter(val context: Context, private val list: List<PetBibleBean>?, private val itemClick: (info: PetBibleBean) -> Unit) : RecyclerView.Adapter<PetBibleAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pet_bible, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_icon)
        holder.itemView.tv_title.text = info.NAME

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}