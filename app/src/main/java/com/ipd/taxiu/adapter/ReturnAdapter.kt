package com.ipd.taxiu.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ReturnBean
import com.ipd.taxiu.utils.StringUtils
import kotlinx.android.synthetic.main.item_return_list.view.*


/**
 * Created by Miss on 2018/7/19
 * 退款退货adapter
 */
class ReturnAdapter(private val context: Context, private val list: ArrayList<ReturnBean>?, private val itemClick: (info: ReturnBean) -> Unit) : RecyclerView.Adapter<ReturnAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_return_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.tv_return_reason.text = "退货原因：${info.REASON}"
        val reasonStr = holder.itemView.tv_return_reason.text.toString().trim()
        val ss = SpannableString(reasonStr)
        val colorSpan = ForegroundColorSpan(Color.parseColor("#EC601E"))
        ss.setSpan(colorSpan, 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        holder.itemView.tv_return_reason.text = ss


        val pics = StringUtils.splitImages(info.PIC)
        if (pics.isNotEmpty()) {
            holder.itemView.picture_recycler_view.visibility = View.VISIBLE
            holder.itemView.picture_recycler_view.adapter = ReturnPictureAdapter(context, pics, { list, pos ->
                //                PictureLookActivity.launch(context as Activity?, ArrayList(list), pos, PictureLookActivity.URL)
            })
            holder.itemView.picture_recycler_view.setOnTouchListener { v, event ->
                holder.itemView.onTouchEvent(event)
            }
        } else {
            holder.itemView.picture_recycler_view.visibility = View.GONE
        }

        holder.itemView.tv_order_number.text = info.ORDER_NO
        holder.itemView.tv_submit_time.text = "提交时间： ${info.CREATETIME}"
        holder.itemView.tv_return_type.text = if (info.CATEGORY == 1) "仅退款" else "退款退货"

        holder.itemView.setOnClickListener {
            itemClick?.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
