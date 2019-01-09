package com.ipd.rainbow.ui.fragment

import android.os.Bundle
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIFragment
import kotlinx.android.synthetic.main.base_toolbar.view.*

class MineFragment : BaseUIFragment() {

    override fun getContentLayout(): Int = R.layout.fragment_store

    override fun initTitle() {
        super.initTitle()
        mHeaderView.tv_title.text = "我的"
    }

    override fun initView(bundle: Bundle?) {

    }

    override fun loadData() {
    }

    override fun initListener() {
    }

}