package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.MainActivity
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.CartProductBean
import com.ipd.taxiu.bean.EmptyCartProductBean
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.bean.RecommendProductHeaderBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.utils.CartCallback
import com.ipd.taxiu.widget.CartOperationView
import com.ipd.taxiu.widget.CartRecyclerView
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.layout_empty_cart.view.*

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
                holder.itemView.tv_go_shopping.setOnClickListener {
                    if (context is MainActivity) {
                        context.switchToStore()
                    }
                }
            }
            CartRecyclerView.CartType.CART_PRODUCT -> {
                val cartProductInfo = list!![position] as CartProductBean

                ImageLoader.loadNoPlaceHolderImg(context, cartProductInfo.PRODUCT.LOGO, holder.itemView.iv_cart_product_img)
                holder.itemView.tv_cart_product_name.text = cartProductInfo.PRODUCT.PROCUCT_NAME
                holder.itemView.tv_cart_product_spec.text = cartProductInfo.PRODUCT.TASTE
                holder.itemView.tv_cart_product_lable.text = cartProductInfo.PRODUCT.TASTE
                holder.itemView.tv_cart_product_price.text = "￥${cartProductInfo.PRODUCT.CURRENT_PRICE}"
                holder.itemView.cart_operation_view.setNum(cartProductInfo.NUM)

                holder.itemView.cart_operation_view.setOnCartNumChangeListener(object : CartOperationView.OnCartNumChangeListener {
                    override fun onNumChange(lastNum: Int, num: Int) {
                        //修改数量
                        holder.itemView.cart_operation_view.setEnable(false)
                        cartCallback.onCartProductNumChange(cartProductInfo.CART_ID, num, {
                            //修改购物车model数量及价格
                            holder.itemView.cart_operation_view.setEnable(true)
                            if (it) {
                                cartProductInfo.NUM = num
                                cartProductInfo.SUB_TOTAL = (cartProductInfo.PRODUCT.CURRENT_PRICE.toDouble() * cartProductInfo.NUM).toString()
                                cartCallback.onCartProductCheckChange()
                            } else {
                                holder.itemView.cart_operation_view.setNum(lastNum)
                            }
                        })
                    }
                })

                holder.itemView.tv_cart_product_lable.visibility = if (cartProductInfo.PRODUCT.KIND == 1) View.GONE else View.VISIBLE
                holder.itemView.tv_cart_product_lable.text = cartProductInfo.PRODUCT.kindStr

                holder.itemView.cb_product.isChecked = cartProductInfo.isChecked
                holder.itemView.setOnClickListener {
                    //选中、取消选中
                    cartProductInfo.isChecked = !cartProductInfo.isChecked
                    holder.itemView.cb_product.isChecked = cartProductInfo.isChecked
                    cartCallback.onCartProductCheckChange()
                }
                holder.itemView.iv_cart_product_delete.setOnClickListener {
                    //删除
                    cartCallback.onDelete(position, cartProductInfo)
                }
            }
            CartRecyclerView.CartType.RECOMMEND_PRODUCT_HEADER -> {
                holder.itemView.setOnClickListener {
                }
            }
            CartRecyclerView.CartType.RECOMMEND_PRODUCT -> {
                val info = list!![position] as ProductBean

                ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_product_img)
                holder.itemView.tv_product_name.text = info.PROCUCT_NAME
                holder.itemView.tv_product_price.text = "￥${info.CURRENT_PRICE}"
                holder.itemView.tv_product_evalute.text = "评价 ${info.REPLY}"
                holder.itemView.tv_product_sales.text = "销量 ${info.BUYNUM}"

                holder.itemView.setOnClickListener {
                    cartCallback.onRecommendProductItemClick(list!![position] as ProductBean)
                }
            }
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}