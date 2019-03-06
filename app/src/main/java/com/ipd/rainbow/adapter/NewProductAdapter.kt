package com.ipd.rainbow.adapter

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.utils.StringUtils
import kotlinx.android.synthetic.main.item_product_sales.view.*
import kotlinx.android.synthetic.main.item_product_sales_grid.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class NewProductAdapter(val context: Context, private val list: List<ProductBean>?, private val itemClick: (info: ProductBean) -> Unit) : RecyclerView.Adapter<NewProductAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0

    object ItemType {
        const val LIST = 0
        const val GRID = 1
    }

    var mType: Int = ItemType.LIST

    override fun getItemViewType(position: Int): Int {
        return mType
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.LIST -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_sales, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_sales_grid, parent, false))
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        when (getItemViewType(position)) {
            ProductAdapter.ItemType.LIST -> {
                holder.itemView.iv_new_product.visibility = if (info.isNew) View.VISIBLE else View.GONE

                if (info.isSales) {
                    //是否为特价
                    holder.itemView.tv_product_price_old.visibility = View.VISIBLE
                    holder.itemView.tv_product_price_old.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
                    holder.itemView.tv_product_price_old.text = "￥${info.PRICE}"
                } else {
                    holder.itemView.tv_product_price_old.visibility = View.GONE
                }

                ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_product_img)
                holder.itemView.tv_product_name.text = info.NAME
                holder.itemView.tv_product_price.text = "${StringUtils.getEncryPrice(true, info.CURRENT_PRICE, info.KIND)}"
            }
            ProductAdapter.ItemType.GRID -> {
                holder.itemView.iv_new_product_grid.visibility = if (info.isNew) View.VISIBLE else View.GONE

                if (info.isSales) {
                    //是否为特价
                    holder.itemView.tv_product_old_price_grid.visibility = View.VISIBLE
                    holder.itemView.tv_product_old_price_grid.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
                    holder.itemView.tv_product_old_price_grid.text = "￥${info.PRICE}"
                } else {
                    holder.itemView.tv_product_old_price_grid.visibility = View.GONE
                }

                ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_product_img_grid)
                holder.itemView.tv_product_name_grid.text = info.NAME

                holder.itemView.tv_price_unit.visibility = if (StringUtils.priceNeedEncry(info.CURRENT_PRICE, info.KIND)) View.GONE else View.VISIBLE
                holder.itemView.tv_product_price_grid.text = StringUtils.getEncryPrice(false, info.CURRENT_PRICE, info.KIND)
            }
        }

        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}