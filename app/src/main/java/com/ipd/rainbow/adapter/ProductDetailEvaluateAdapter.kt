package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductDetailBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.utils.StringUtils
import kotlinx.android.synthetic.main.item_product_detail_evaluate.view.*

class ProductDetailEvaluateAdapter(val context: Context, private val data: List<ProductDetailBean.ProductEvaluateBean>?, private val itemClick: (info: ProductDetailBean.ProductEvaluateBean) -> Unit) : RecyclerView.Adapter<ProductDetailEvaluateAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_detail_evaluate, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = data!![position]
        val pics = StringUtils.splitImages(info.PIC)
        if (pics == null || pics.isEmpty()) {
            holder.itemView.iv_evaluate_img.visibility = View.GONE
        } else {
            holder.itemView.iv_evaluate_img.visibility = View.VISIBLE
            ImageLoader.loadNoPlaceHolderImg(context, pics[0], holder.itemView.iv_evaluate_img)
            holder.itemView.tv_evaluate_num.text = "${pics.size}张"

//            holder.itemView.iv_evaluate_img.setOnClickListener {
//                PictureLookActivity.launch(context as Activity, ArrayList(pics),0,PictureLookActivity.URL)
//            }

        }
        ImageLoader.loadNoPlaceHolderImg(context, info.USER_LOGO, holder.itemView.civ_avatar)
        holder.itemView.tv_nickname.text = info.USER_NICKNAME
        holder.itemView.tv_content.text = info.CONTENT

        holder.itemView.setOnClickListener {
            itemClick?.invoke(info)
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}