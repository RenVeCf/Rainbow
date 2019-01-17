package com.ipd.rainbow.utils

import com.ipd.rainbow.bean.CartProductBean
import com.ipd.rainbow.bean.ProductBean

interface CartCallback {
    fun onDelete(pos: Int, cartProductBean: CartProductBean)
    fun onCartProductNumChange(cartId: Int, num: Int, callback: (isSuccess: Boolean) -> Unit)
    fun onCartProductCheckChange()
}