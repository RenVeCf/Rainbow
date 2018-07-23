package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.bean.StoreIndexCategoryBean
import com.ipd.taxiu.bean.TaxiuBean

/**
 * Created by jumpbox on 2017/8/31.
 */
class StoreIndexCategoryAdapter(val context: Context, private val list: List<StoreIndexCategoryBean>?, private val itemClick: (info: StoreIndexCategoryBean) -> Unit) : RecyclerView.Adapter<StoreIndexCategoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_store_menu_category, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}