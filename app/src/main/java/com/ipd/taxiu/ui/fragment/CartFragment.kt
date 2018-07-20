package com.ipd.taxiu.ui.fragment

import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIFragment
import kotlinx.android.synthetic.main.base_toolbar.view.*

class CartFragment : BaseUIFragment() {

    override fun getContentLayout(): Int = R.layout.fragment_cart

    override fun initTitle() {
        super.initTitle()
        mHeaderView.tv_title.text = "购物车"
    }

    override fun initView(bundle: Bundle?) {

    }

    override fun loadData() {
    }

    override fun initListener() {
    }

}