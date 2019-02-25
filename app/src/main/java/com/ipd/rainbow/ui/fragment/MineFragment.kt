package com.ipd.rainbow.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.UserHomeBean
import com.ipd.rainbow.event.UpdateUserInfoEvent
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.presenter.MinePresenter
import com.ipd.rainbow.ui.BaseUIFragment
import com.ipd.rainbow.ui.activity.balance.MyBalanceActivity
import com.ipd.rainbow.ui.activity.coupon.DiscountCouponActivity
import com.ipd.rainbow.ui.activity.coupon.MyIntegralActivity
import com.ipd.rainbow.ui.activity.group.GroupBookingActivity
import com.ipd.rainbow.ui.activity.mine.MineCollectActivity
import com.ipd.rainbow.ui.activity.mine.UserInfoActivity
import com.ipd.rainbow.ui.activity.mine.VipActivity
import com.ipd.rainbow.ui.activity.order.MyOrderActivity
import com.ipd.rainbow.ui.activity.order.ReturnMoneyCommodityActivity
import com.ipd.rainbow.ui.activity.setting.SettingActivity
import kotlinx.android.synthetic.main.fragment_mine_new.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MineFragment : BaseUIFragment(), MinePresenter.IUserHomeView {
    override fun getInfoSuccess(data: UserHomeBean) {
        ImageLoader.loadAvatar(mActivity, data.LOGO, mContentView.civ_avatar)
        mContentView.tv_nickname.text = data.NICKNAME
        mContentView.rl_wait_pay.setCircleNum(data.WAIT_PAY)
        mContentView.rl_wait_shipments.setCircleNum(data.WAIT_SEND)
        mContentView.rl_wait_delivery.setCircleNum(data.WAIT_RECEIPT)
        mContentView.iv_open_vip.visibility = if (data.OPEN_VIP == 0) View.GONE else View.VISIBLE
    }

    override fun getInfoFail(errMsg: String) {
        loadData()
    }

    override fun getTitleLayout(): Int = -1

    override fun getContentLayout(): Int = R.layout.fragment_mine_new


    private var mPresenter: MinePresenter<MinePresenter.IUserHomeView>? = null
    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
        mPresenter = MinePresenter()
        mPresenter?.attachView(mActivity, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
        mPresenter?.detachView()
        mPresenter = null
    }


    override fun initView(bundle: Bundle?) {

    }

    override fun loadData() {
        mPresenter?.getUserHome()
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
        mContentView.ll_vip.setOnClickListener {
            //vip
            VipActivity.launch(mActivity)
        }
        mContentView.ll_group_purchase.setOnClickListener {
            //我的拼团
            GroupBookingActivity.launch(mActivity)
        }
        mContentView.ll_coupon.setOnClickListener {
            //优惠券
            DiscountCouponActivity.launch(mActivity)
        }
        mContentView.ll_return_record.setOnClickListener {
            //退款记录
            val intent = Intent(mActivity, ReturnMoneyCommodityActivity::class.java)
            startActivity(intent)
        }
        mContentView.ll_balance.setOnClickListener {
            //我的余额
            MyBalanceActivity.launch(mActivity)
        }
        mContentView.ll_integral.setOnClickListener {
            //我的积分
            val intent = Intent(mActivity, MyIntegralActivity::class.java)
            startActivity(intent)
        }
        mContentView.ll_collect.setOnClickListener {
            //我的收藏
            MineCollectActivity.launch(mActivity)
        }
        mContentView.ll_setting.setOnClickListener {
            //设置
            val intent = Intent(mActivity, SettingActivity::class.java)
            startActivity(intent)
        }

    }

    private fun startOrder(status: Int) {
        val intent = Intent(activity, MyOrderActivity::class.java)
        intent.putExtra("status", status)
        mActivity.startActivity(intent)
    }

    @Subscribe
    fun onMainEvent(event: UpdateUserInfoEvent) {
        loadData()
    }

}