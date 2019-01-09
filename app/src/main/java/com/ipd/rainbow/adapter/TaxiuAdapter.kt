package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.TaxiuBean
import kotlinx.android.synthetic.main.item_list_taxiu.view.*
import kotlinx.android.synthetic.main.item_taxiu.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class TaxiuAdapter(val context: Context, private val list: List<TaxiuBean>?, private val itemClick: (info: TaxiuBean) -> Unit) : RecyclerView.Adapter<TaxiuAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_taxiu, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        holder.itemView.taxiu_layout.setData(info)

        holder.itemView.taxiu_layout.media_recycler_view.setOnTouchListener{v, event ->
            holder.itemView.onTouchEvent(event)
        }

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}