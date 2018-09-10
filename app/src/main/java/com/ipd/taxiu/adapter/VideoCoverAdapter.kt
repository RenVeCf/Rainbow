package com.ipd.taxiu.adapter

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import kotlinx.android.synthetic.main.item_video_cover.view.*

/**
 * Created by jumpbox on 2017/7/23.
 */

class VideoCoverAdapter(private val mContext: Context, private val list: ArrayList<Bitmap>, val listener: VideoCoverListener) : RecyclerView.Adapter<VideoCoverAdapter.ViewHolder>() {

    private var mCurPos = -1

    fun addBitmap(bitmap: Bitmap) {
        if (list.isEmpty()) {
            mCurPos = 0
            listener.onFirstInited(bitmap)
        }
        list.add(bitmap)
        notifyDataSetChanged()
    }

    fun getSelectedBitmap(): Bitmap? {
        if (mCurPos >= 0) {
            return list[mCurPos]
        }
        return null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_video_cover, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        if (position == mCurPos) {
            holder.itemView.unselected_view.visibility = View.GONE
        } else {
            holder.itemView.unselected_view.visibility = View.VISIBLE
        }
        holder.itemView.thumb.setImageBitmap(info)


        holder.itemView.setOnClickListener {
            val lastPosition = mCurPos
            mCurPos = position
            if (lastPosition >= 0)
                notifyItemChanged(lastPosition)
            notifyItemChanged(mCurPos)
            listener?.onSelectChanged(info)
        }

    }

    override fun getItemCount(): Int = list?.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface VideoCoverListener {
        fun onFirstInited(bitmap: Bitmap)
        fun onSelectChanged(bitmap: Bitmap)
    }


}
