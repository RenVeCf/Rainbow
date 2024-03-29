package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_return_picture_img.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class ReturnPictureAdapter(val context: Context, private val list: List<String>?, val itemClick: ((list: List<String>, pos: Int) -> Unit)?) : RecyclerView.Adapter<ReturnPictureAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_return_picture_img, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        ImageLoader.loadNoPlaceHolderImg(context, info, holder.itemView.iv_image)
        if (itemClick != null) {
            holder.itemView.setOnClickListener {
                itemClick.invoke(list, position)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}