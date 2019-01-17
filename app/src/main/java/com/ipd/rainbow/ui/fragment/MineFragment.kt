package com.ipd.rainbow.ui.fragment

import android.content.Intent
import android.os.Bundle
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIFragment
import com.ipd.rainbow.ui.activity.mine.UserInfoActivity
import com.ipd.rainbow.ui.activity.order.MyOrderActivity
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
        mContentView.rl_wait_pay.setOnClickListener {
            startOrder(1)
        }
        mContentView.rl_wait_shipments.setOnClickListener {
            startOrder(2)
        }
        mContentView.rl_wait_delivery.setOnClickListener {
            startOrder(3)
        }
        mContentView.rl_off_the_stocks.setOnClickListener {
            startOrder(4)
        }
        mContentView.rl_all_order.setOnClickListener {
            startOrder(0)
        }



    }

    private fun startOrder(status: Int) {
        val intent = Intent(activity, MyOrderActivity::class.java)
        intent.putExtra("status", status)
        mActivity.startActivity(intent)
    }

}