package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.CartProductBean
import com.ipd.taxiu.bean.EmptyCartProductBean
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.bean.RecommendProductHeaderBean
import com.ipd.taxiu.utils.CartCallback
import com.ipd.taxiu.widget.CartRecyclerView
import kotlinx.android.synthetic.main.item_cart.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class CartAdapter(val context: Context, private val list: List<Any>?, val cartCallback: CartCallback) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        return when (list!![position]) {
            is EmptyCartProductBean -> CartRecyclerView.CartType.EMPTY_CART
            is CartProductBean -> CartRecyclerView.CartType.CART_PRODUCT
            is RecommendProductHeaderBean -> CartRecyclerView.CartType.RECOMMEND_PRODUCT_HEADER
            is ProductBean -> CartRecyclerView.CartType.RECOMMEND_PRODUCT
            else -> throw IllegalArgumentException("list data error...")
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when (viewType) {
            CartRecyclerView.CartType.EMPTY_CART -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_empty_cart, parent, false))
            CartRecyclerView.CartType.CART_PRODUCT -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false))
            CartRecyclerView.CartType.RECOMMEND_PRODUCT_HEADER -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart_recommend_header, parent, false))
            else -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_grid, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            CartRecyclerView.CartType.EMPTY_CART -> {
                holder.itemView.setOnClickListener {
                }
            }
            CartRecyclerView.CartType.CART_PRODUCT -> {
                val cartProductInfo = list!![position] as CartProductBean
                holder.itemView.cb_product.isChecked = cartProductInfo.isChecked
                holder.itemView.setOnClickListener {
                    cartProductInfo.isChecked = !cartProductInfo.isChecked
                    holder.itemView.cb_product.isChecked = cartProductInfo.isChecked
                }
                holder.itemView.iv_cart_product_delete.setOnClickListener {
                    cartCallback.onDelete(position, cartProductInfo)
                }
            }
            CartRecyclerView.CartType.RECOMMEND_PRODUCT_HEADER -> {
                holder.itemView.setOnClickListener {
                }
            }
            CartRecyclerView.CartType.RECOMMEND_PRODUCT -> {
                holder.itemView.setOnClickListener {
                    cartCallback.onRecommendProductItemClick(list!![position] as ProductBean)
                }
            }
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}