package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ProductCategoryAllBean
import com.ipd.taxiu.bean.ProductCategoryChildBean
import com.ipd.taxiu.bean.ProductCategoryTitleBean
import com.ipd.taxiu.bean.StoreIndexBrandBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.ui.activity.store.ProductListActivity
import kotlinx.android.synthetic.main.item_product_category.view.*
import kotlinx.android.synthetic.main.item_product_category_title.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class ProductCategoryAdapter(val context: Context, private val list: List<Any>?, val itemClick: () -> Unit) : RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0

    object ItemType {
        const val TITLE = 0
        const val CONTENT = 1
        const val ALL = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (list!![position]) {
            is ProductCategoryTitleBean -> {
                ItemType.TITLE
            }
            is ProductCategoryAllBean -> {
                ItemType.ALL
            }
            else -> {
                ItemType.CONTENT
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.TITLE -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_category_title, parent, false))
            }
            ItemType.ALL -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_category_all, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_category, parent, false))
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.TITLE -> {
                val info = list!![position] as ProductCategoryTitleBean
                holder.itemView.tv_category_title.text = info.titleName

            }
            ItemType.ALL -> {
                val info = list!![position] as ProductCategoryAllBean
                holder.itemView.setOnClickListener {
                    if (info.type == 0) {
                        ProductListActivity.launch(context as Activity, info.name)
                    } else {
                        //全部品牌
                        itemClick.invoke()
                    }
                }
            }
            ItemType.CONTENT -> {
                val info = list!![position]
                if (info is ProductCategoryChildBean.TIPBean) {
                    ImageLoader.loadNoPlaceHolderImg(context, info.ICON, holder.itemView.iv_category_img)
                    holder.itemView.tv_category_name.text = info.TIP_NAME
                    holder.itemView.setOnClickListener {
                        ProductListActivity.launch(context as Activity, info.TIP_NAME)
                    }
                } else if (info is StoreIndexBrandBean) {
                    ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_category_img)
                    holder.itemView.tv_category_name.text = info.BRAND_NAME
                    holder.itemView.setOnClickListener {
                        ProductListActivity.launch(context as Activity, info.BRAND_NAME)
                    }
                }
            }
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}