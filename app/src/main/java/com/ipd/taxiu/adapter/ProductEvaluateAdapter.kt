package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ProductEvaluateBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.ui.activity.PictureLookActivity
import com.ipd.taxiu.utils.StringUtils
import kotlinx.android.synthetic.main.item_product_evaluate.view.*
import java.util.*

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

        ImageLoader.loadAvatar(context, info.USER_LOGO, holder.itemView.civ_avatar)
        holder.itemView.tv_username.text = info.USER_NICKNAME
        holder.itemView.tv_evaluate_time.text = info.CREATETIME
        holder.itemView.rating_star.setStar(info.TOTAL_SCORE.toFloat())
        holder.itemView.tv_answer_content.text = info.CONTENT
        if (TextUtils.isEmpty(info.PIC)) {
            holder.itemView.image_recycler_view.visibility = View.GONE
        } else {
            holder.itemView.image_recycler_view.visibility = View.VISIBLE
            holder.itemView.image_recycler_view.layoutManager = GridLayoutManager(context, 4)
            holder.itemView.image_recycler_view.adapter = MediaPictureAdapter(context, StringUtils.splitImages(info.PIC), { list, pos ->
                PictureLookActivity.launch(context as Activity, ArrayList(list),pos,PictureLookActivity.URL)
            })
        }


        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}