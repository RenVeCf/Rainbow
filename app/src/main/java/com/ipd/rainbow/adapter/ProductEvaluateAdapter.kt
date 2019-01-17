package com.ipd.rainbow.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductEvaluateBean
import com.ipd.rainbow.bean.ProductEvaluateHeaderBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.ui.activity.PictureLookActivity
import com.ipd.rainbow.utils.StringUtils
import kotlinx.android.synthetic.main.item_product_evaluate.view.*
import kotlinx.android.synthetic.main.item_product_evaluate_header.view.*
import java.util.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class ProductEvaluateAdapter(val context: Context, private val list: List<Any>?, private val itemClick: (info: Any) -> Unit) : RecyclerView.Adapter<ProductEvaluateAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0

    val HEADER = 0
    val CONTENT = 1

    override fun getItemViewType(position: Int): Int {
        val info = list!![position]
        return when (info) {
            is ProductEvaluateHeaderBean -> HEADER
            else -> CONTENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return if (viewType == HEADER) {
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_evaluate_header, parent, false))
        } else {
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_evaluate, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            HEADER -> {
                val info = list!![position] as ProductEvaluateHeaderBean
                holder.itemView.tv_evaluate_num.text = "商品评价（${info.num}）"
                holder.itemView.tv_evaluate_percent.text = info.percent + "%"
            }
            CONTENT -> {
                val info = list!![position] as ProductEvaluateBean

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
                        PictureLookActivity.launch(context as Activity, ArrayList(list), pos, PictureLookActivity.URL)
                    })
                }


                holder.itemView.setOnClickListener {
                    itemClick.invoke(info)
                }
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}