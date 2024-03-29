package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.MenuCategoryBean
import com.ipd.rainbow.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_store_menu_category.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class MenuCategoryAdapter(val context: Context, private val list: List<MenuCategoryBean>?, private val itemClick: (info: MenuCategoryBean) -> Unit) : RecyclerView.Adapter<MenuCategoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_store_menu_category, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_category_img)
        holder.itemView.tv_category_name.text = info.title

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}