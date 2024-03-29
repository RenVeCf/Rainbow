package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductEvaluateBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.utils.StringUtils
import kotlinx.android.synthetic.main.item_product_evaluate.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class ProductEvaluateAdapter(val context: Context, private val list: List<ProductEvaluateBean>?, private val itemClick: (info: ProductEvaluateBean) -> Unit) : RecyclerView.Adapter<ProductEvaluateAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_evaluate, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        val pics = StringUtils.splitImages(info.PIC)
        if (pics == null || pics.isEmpty()) {
            holder.itemView.iv_evaluate_img.visibility = View.GONE
        } else {
            holder.itemView.iv_evaluate_img.visibility = View.VISIBLE
            ImageLoader.loadNoPlaceHolderImg(context, pics[0], holder.itemView.iv_evaluate_img)
            holder.itemView.tv_evaluate_num.text = "${pics.size}张"
        }
        ImageLoader.loadNoPlaceHolderImg(context, info.USER_LOGO, holder.itemView.civ_avatar)
        holder.itemView.tv_nickname.text = info.USER_NICKNAME
        holder.itemView.tv_content.text = info.CONTENT

        holder.itemView.rating_star.setStar(info.TOTAL_SCORE.toFloat())

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}