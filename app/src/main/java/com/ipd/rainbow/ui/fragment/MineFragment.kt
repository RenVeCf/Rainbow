package com.ipd.rainbow.ui.fragment

import android.os.Bundle
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIFragment
import com.ipd.rainbow.ui.activity.mine.UserInfoActivity
import kotlinx.android.synthetic.main.fragment_mine_new.view.*

class MineFragment : BaseUIFragment() {

    override fun getTitleLayout(): Int = -1

    override fun getContentLayout(): Int = R.layout.fragment_mine_new

    override fun initView(bundle: Bundle?) {

    }

    override fun loadData() {
    }

    override fun initListener() {
        mContentView.civ_avatar.setOnClickListener {
            UserInfoActivity.launch(mActivity)
        }
    }

}