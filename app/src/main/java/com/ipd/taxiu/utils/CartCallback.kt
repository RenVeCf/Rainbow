package com.ipd.taxiu.utils

import com.ipd.taxiu.bean.CartProductBean
import com.ipd.taxiu.bean.ProductBean

interface CartCallback {
    fun onDelete(pos: Int, cartProductBean: CartProductBean)
    fun onRecommendProductItemClick(productBean: ProductBean)
    fun onCartProductNumChange(cartId: Int, num: Int, callback: (isSuccess: Boolean) -> Unit)
    fun onCartProductCheckChange()
}