package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.CartProductBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.utils.CartCallback
import com.ipd.rainbow.widget.CartOperationView
import kotlinx.android.synthetic.main.item_cart.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class CartAdapter(val context: Context, private val list: List<CartProductBean>?, val cartCallback: CartCallback) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val cartProductInfo = list!![position]

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
                cartCallback.onCartProductNumChange(cartProductInfo.CART_ID, num) {
                    //修改购物车model数量及价格
                    holder.itemView.cart_operation_view.setEnable(true)
                    if (it) {
                        cartProductInfo.NUM = num
                        cartProductInfo.SUB_TOTAL = (cartProductInfo.PRODUCT.CURRENT_PRICE.toDouble() * cartProductInfo.NUM).toString()
                        cartCallback.onCartProductCheckChange()
                    } else {
                        holder.itemView.cart_operation_view.setNum(lastNum)
                    }
                }
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}