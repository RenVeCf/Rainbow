package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductParamBean
import kotlinx.android.synthetic.main.item_product_param.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class ProductParamAdapter(val context: Context, private val list: List<ProductParamBean>?, private val itemClick: (info: ProductParamBean) -> Unit) : RecyclerView.Adapter<ProductParamAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_param, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.tv_param_name.text = info.TITLE
        holder.itemView.tv_param_value.text = info.CONTENT

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}