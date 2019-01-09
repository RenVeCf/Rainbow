package com.ipd.rainbow.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductCategoryAllBean
import com.ipd.rainbow.bean.ProductCategoryChildBean
import com.ipd.rainbow.bean.ProductCategoryTitleBean
import com.ipd.rainbow.bean.StoreIndexBrandBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.ui.activity.store.ProductListActivity
import kotlinx.android.synthetic.main.item_product_category.view.*
import kotlinx.android.synthetic.main.item_product_category_title.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class ProductCategoryAdapter(val context: Context, private val list: List<Any>?, val itemClick: () -> Unit) : RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0

    object ItemType {
        const val TITLE = 0
        const val ALL = 2
        const val PRODUCT = 1
        const val BRAND = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (list!![position]) {
            is ProductCategoryTitleBean -> {
                ItemType.TITLE
            }
            is ProductCategoryAllBean -> {
                ItemType.ALL
            }
            is ProductCategoryChildBean.TIPBean -> {
                ItemType.PRODUCT
            }
            else -> {
                ItemType.BRAND
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
            ItemType.PRODUCT -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_category_product, parent, false))
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
            else -> {
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