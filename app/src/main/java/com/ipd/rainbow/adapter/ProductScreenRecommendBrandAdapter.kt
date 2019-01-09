package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductBrandBean
import kotlinx.android.synthetic.main.item_product_screen_brand.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class ProductScreenRecommendBrandAdapter(val context: Context, private val list: List<ProductBrandBean>?) : RecyclerView.Adapter<ProductScreenRecommendBrandAdapter.ViewHolder>() {

    private var mCurCheckedPos = -1
    override fun getItemCount(): Int = list?.size ?: 0

    fun getCurCheckedPos(): Int = mCurCheckedPos

    fun reset() {
        if (mCurCheckedPos == -1) return
        val lastPos = mCurCheckedPos
        mCurCheckedPos = -1
        notifyItemChanged(lastPos)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_screen_brand, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        holder.itemView.tv_brand_name.text = info.BRAND_NAME

        holder.itemView.iv_brand_check.visibility = if (mCurCheckedPos == position) View.VISIBLE else View.INVISIBLE

        holder.itemView.setOnClickListener {
            if (mCurCheckedPos != -1 && mCurCheckedPos != position) notifyItemChanged(mCurCheckedPos)

            mCurCheckedPos = if (mCurCheckedPos == position) -1 else position
            holder.itemView.iv_brand_check.visibility = if (mCurCheckedPos == position) View.VISIBLE else View.INVISIBLE
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}