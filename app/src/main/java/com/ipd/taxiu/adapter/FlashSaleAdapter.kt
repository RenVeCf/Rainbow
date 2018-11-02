package com.ipd.taxiu.adapter

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.FlashSaleProductBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.utils.StoreType
import kotlinx.android.synthetic.main.item_flash_sale_header.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class FlashSaleAdapter(val context: Context, private val todayProduct: FlashSaleProductBean?, private val list: List<FlashSaleProductBean>?, private val itemClick: (itemClickType: Int, info: FlashSaleProductBean) -> Unit, val tabListener: (pos: Int) -> Unit) : RecyclerView.Adapter<FlashSaleAdapter.ViewHolder>() {

    object ItemClickType {
        const val ITEM = 0
        const val PURCHASE = 1
        const val REMIND_ME = 2
        const val CANCEL_REMIND = 3
    }


    override fun getItemCount(): Int = (list?.size ?: 0).plus(1)

    object ItemType {
        const val HEADER = 0
        const val CONTENT = 1
    }

    var mType: Int = StoreType.FLASH_SALE_TODAY


    override fun getItemViewType(position: Int): Int {
        return if (position == 0) ItemType.HEADER else ItemType.CONTENT
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.HEADER -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_flash_sale_header, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_flash_sale_product, parent, false))
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.HEADER -> {
                if (todayProduct == null) {
                    holder.itemView.cl_today_product.visibility = View.GONE
                } else {
                    holder.itemView.cl_today_product.visibility = View.VISIBLE
                    setItemData(true, holder, todayProduct)
                }

                holder.itemView.tab_layout.setTabListener {
                    tabListener?.invoke(it)
                }

            }
            ItemType.CONTENT -> {
                val info = list!![position - 1]
                setItemData(false, holder, info)

                holder.itemView.setOnClickListener { itemClick.invoke(ItemClickType.ITEM, info) }
            }
        }
    }

    private fun setItemData(isHeader: Boolean, holder: ViewHolder, info: FlashSaleProductBean) {
        ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_cheapest_img)
        holder.itemView.tv_cheapest_name.text = info.PROCUCT_NAME
        holder.itemView.sale_progress.max = 100
        holder.itemView.sale_progress.progress = (info.PROGRESS * 100).toInt()
        holder.itemView.tv_cheapest_progress.text = String.format(context.getString(R.string.product_purchase_num), info.BUY_PEOPLE)
        holder.itemView.tv_cheapest_price.text = "￥${info.CURRENT_PRICE}"
        holder.itemView.tv_cheapest_old_price.text = "￥${info.REFER_PRICE}"
        holder.itemView.tv_cheapest_old_price.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG

        if (isHeader || mType == StoreType.FLASH_SALE_TODAY) {
            holder.itemView.tv_cheapest_buy.text = "马上抢"
            holder.itemView.tv_cheapest_buy.setBackgroundResource(R.drawable.shape_buy_bg)

            holder.itemView.tv_cheapest_buy.setOnClickListener {
                itemClick.invoke(ItemClickType.PURCHASE, info)
            }
        } else {
            if (info.IS_REMIND == 0) {
                holder.itemView.tv_cheapest_buy.setBackgroundResource(R.drawable.shape_remind_me_bg)
                holder.itemView.tv_cheapest_buy.text = "提醒我"

                holder.itemView.tv_cheapest_buy.setOnClickListener {
                    itemClick.invoke(ItemClickType.REMIND_ME, info)
                }
            } else {
                holder.itemView.tv_cheapest_buy.setBackgroundResource(R.drawable.shape_reminded_bg)
                holder.itemView.tv_cheapest_buy.text = "取消"

                holder.itemView.tv_cheapest_buy.setOnClickListener {
                    itemClick.invoke(ItemClickType.CANCEL_REMIND, info)
                }
            }

        }


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}