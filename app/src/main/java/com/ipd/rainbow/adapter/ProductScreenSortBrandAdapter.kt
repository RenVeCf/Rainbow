package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductBrandBean
import com.ipd.rainbow.bean.ProductExpertScreenBean
import kotlinx.android.synthetic.main.item_index_title.view.*
import kotlinx.android.synthetic.main.item_product_screen_brand.view.*
import me.yokeyword.indexablerv.IndexableAdapter

/**
 * Created by jumpbox on 2017/8/31.
 */
class ProductScreenSortBrandAdapter(val context: Context) : IndexableAdapter<ProductBrandBean>() {

    private var mCheckedScreenInfo: ProductExpertScreenBean.ScreenInfo? = null

    fun getCheckedScreenInfo(): ProductExpertScreenBean.ScreenInfo? = mCheckedScreenInfo

    fun reset() {
        mCheckedScreenInfo = null
        notifyDataSetChanged()
    }

    override fun onCreateTitleViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_screen_brand_index_title, parent, false))
    }

    override fun onCreateContentViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_screen_brand, parent, false))
    }

    override fun onBindTitleViewHolder(holder: RecyclerView.ViewHolder, indexTitle: String) {
        holder.itemView.tv_index.text = indexTitle
    }


    override fun onBindContentViewHolder(holder: RecyclerView.ViewHolder, info: ProductBrandBean) {
        holder.itemView.tv_brand_name.text = info.BRAND_NAME

        holder.itemView.iv_brand_check.visibility = if (mCheckedScreenInfo?.MODULE_ID == info.BRAND_ID) View.VISIBLE else View.INVISIBLE

        holder.itemView.setOnClickListener {
            if (mCheckedScreenInfo?.MODULE_ID == info.BRAND_ID) return@setOnClickListener
            mCheckedScreenInfo = ProductExpertScreenBean.ScreenInfo(info.BRAND_ID, info.BRAND_NAME)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}