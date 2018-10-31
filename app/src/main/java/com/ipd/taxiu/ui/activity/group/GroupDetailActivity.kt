package com.ipd.taxiu.ui.activity.group

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.GroupDetailAdapter
import com.ipd.taxiu.adapter.GroupMemberAdapter
import com.ipd.taxiu.bean.GroupOrderDetailBean
import com.ipd.taxiu.presenter.order.GroupOrderDetailPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.utils.StringUtils
import kotlinx.android.synthetic.main.activity_group_detail.*
import kotlinx.android.synthetic.main.item_group_detail_header.view.*
import kotlinx.android.synthetic.main.item_order_detail_footer.view.*
import java.util.*

/**
Created by Miss on 2018/8/10
拼团详情
 */
class GroupDetailActivity : BaseUIActivity(), GroupOrderDetailPresenter.IGroupOrderDetailView {

    companion object {
        fun launch(activity: Activity, orderId: Int) {
            val intent = Intent(activity, GroupDetailActivity::class.java)
            intent.putExtra("orderId", orderId)
            activity.startActivity(intent)
        }
    }

    private val mOrderId by lazy { intent.getIntExtra("orderId", -1) }

    override fun getContentLayout(): Int = R.layout.activity_group_detail
    override fun getToolbarTitle(): String = "拼团详情"


    private var mProductTimerTask: ProductTimerTask? = null
    private val mTimer: Timer = Timer()
    private var mPresenter: GroupOrderDetailPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = GroupOrderDetailPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null

        mProductTimerTask?.cancel()
        mProductTimerTask = null
        mTimer?.cancel()
    }

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        mPresenter?.loadOrderInfo(mOrderId)
    }

    override fun initListener() {
    }

    private var mGroupOrderDetailBean: GroupOrderDetailBean? = null
    override fun loadOrderDetailSuccess(info: GroupOrderDetailBean) {
        mGroupOrderDetailBean = info

        recycler_view_order_detail.layoutManager = LinearLayoutManager(mActivity)
        val adapter = GroupDetailAdapter(mActivity, info.PRODUCT_LIST, info.TEAM_STATUS)
        recycler_view_order_detail.adapter = adapter

        //头部
        val headerView = LayoutInflater.from(this).inflate(R.layout.item_group_detail_header, null)
        headerView.tv_delivery_name.text = info.RECEIVE_NAME
        headerView.tv_delivery_phone.text = info.RECEIVE_PHONE
        headerView.tv_address.text = "${info.PROV} ${info.CITY} ${info.DIST} ${info.ADDRESS}"
        headerView.commodity_number.text = "共${info.PRODUCT_LIST.size}件商品"

        when (info.TEAM_STATUS) {
            1 -> {
                headerView.tv_group.text = "${info.TEAM_NUM}人团，${info.JOIN_NUM}人已参团"
                headerView.tv_group.setTextColor(resources.getColor(R.color.black))

                //倒计时
                mProductTimerTask = ProductTimerTask()
                mTimer.schedule(mProductTimerTask, Date(System.currentTimeMillis()), 1000L)
            }
            2 -> {
                headerView.tv_group.text = "${info.TEAM_NUM}人团，${info.JOIN_NUM}人已参团，拼团成功"
                headerView.tv_group.setTextColor(resources.getColor(R.color.black))
            }
            3 -> {
                headerView.tv_group.text = "${info.TEAM_NUM}人团，${info.JOIN_NUM}人已参团，拼团失败"
                headerView.tv_group.setTextColor(resources.getColor(R.color.red))
            }
        }

        //拼团成员
        headerView.member_recycler_view.adapter = GroupMemberAdapter(mActivity, info.TEAM_NUM, info.USER_LIST, {


        })
        adapter.addHeaderView(headerView)


        //底部
        val footerView = LayoutInflater.from(this).inflate(R.layout.item_order_detail_footer, null)
        footerView.tv_commodity_price.text = "￥${info.TOTAL}"
        footerView.tv_commodity_freight.text = "+￥${info.POST_FEE}"
        footerView.tv_discount_coupon.text = "-￥${info.PREFER_FEE}"
        footerView.actual_price.text = "订单实付金额："
        footerView.tv_actual_price.text = "￥${info.PAY_FEE}"

        footerView.tv_invoice_information.text = when (info.INVOICE_TYPE) {
            1 -> "个人"
            2 -> "单位"
            else -> "不开票"
        }

        if (info.INVOICE_TYPE == 2) {
            footerView.tv_invoice_title.visibility = View.VISIBLE
            footerView.tv_invoice_tax_no.visibility = View.VISIBLE
            footerView.tv_actual_price.text = "发票抬头：${info.INVOICE_HEAD}"
            footerView.tv_invoice_tax_no.text = "公司税号：${info.INVOICE_NUM}"
        } else {
            footerView.tv_invoice_title.visibility = View.GONE
            footerView.tv_invoice_tax_no.visibility = View.GONE
        }
        footerView.tv_order_code.text = info.ORDER_NO

        footerView.tv_copy.setOnClickListener {
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // 将文本内容放到系统剪贴板里。
            cm.text = info.ORDER_NO
            toastShow(true, "复制成功~")
        }

        footerView.tv_orders_time.text = info.CREATETIME
        footerView.payment_time.visibility = View.VISIBLE
        footerView.payment_method.visibility = View.VISIBLE
        footerView.payment_time.text = "付款时间：${info.PAYTIME}"
        footerView.payment_method.text = "支付方式：${StringUtils.getPayStrByPayType(info.PAYWAY)}"
        footerView.tv_shipments_time.visibility = View.GONE
        footerView.tv_delivery_time.visibility = View.GONE

        adapter.addFooterView(footerView)

        showContent()

    }

    override fun loadOrderDetailFail(errMsg: String) {
        showError(errMsg)
    }

    internal inner class ProductTimerTask : TimerTask() {
        override fun run() {
            if (recycler_view_order_detail.layoutManager == null) return
            val layoutManager = recycler_view_order_detail.layoutManager as LinearLayoutManager
            mActivity.runOnUiThread({
                val childView = layoutManager.findViewByPosition(0)
                if (childView != null) {
                    val surplusTime = (mGroupOrderDetailBean?.END_TIME_STAMP
                            ?: "0").toLong() - System.currentTimeMillis()
                    if (surplusTime <= 0) {
                        mTimer?.cancel()
                        mProductTimerTask?.cancel()
                        loadData()
                    }

                    StringUtils.getCountDownByTime(surplusTime, { hours, minutes, second ->
                        childView.tv_group_purchase_hours.text = hours
                        childView.tv_group_purchase_minute.text = minutes
                        childView.tv_group_purchase_second.text = second
                    })

                }
            })
        }
    }
}