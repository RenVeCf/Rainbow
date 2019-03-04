package com.ipd.rainbow.ui.activity.order

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.ipd.rainbow.MainActivity
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.OrderDetailAdapter
import com.ipd.rainbow.bean.OrderDetailBean
import com.ipd.rainbow.bean.WechatBean
import com.ipd.rainbow.event.*
import com.ipd.rainbow.presenter.order.OrderDetailPresenter
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.activity.web.WebActivity
import com.ipd.rainbow.utils.AlipayUtils
import com.ipd.rainbow.utils.Order
import com.ipd.rainbow.utils.StringUtils
import com.ipd.rainbow.utils.WeChatUtils
import com.ipd.rainbow.widget.ChoosePayTypeLayout
import com.ipd.rainbow.widget.MessageDialog
import com.ipd.rainbow.widget.PayWayDialog
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.footer_order_button.*
import kotlinx.android.synthetic.main.item_order_detail_footer.view.*
import kotlinx.android.synthetic.main.item_order_detail_header.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


/**
 * Created by Miss on 2018/7/20
 * 订单详情
 */
class OrderDetailActivity : BaseUIActivity(), View.OnClickListener, OrderDetailPresenter.IOrderDetailView, AlipayUtils.OnPayListener {

    companion object {
        fun launch(activity: Activity, orderId: Int) {
            val intent = Intent(activity, OrderDetailActivity::class.java)
            intent.putExtra("orderId", orderId)
            activity.startActivity(intent)
        }
    }


    private val mOrderId by lazy { intent.getIntExtra("orderId", -1) }
    private var mAdapter: OrderDetailAdapter? = null
    private var mOrderStatus: Int? = null

    override fun getToolbarTitle() = "订单详情"

    override fun getContentLayout() = R.layout.activity_order_detail

    private var mPresenter: OrderDetailPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
        mPresenter = OrderDetailPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
        mPresenter?.detachView()
        mPresenter = null
    }

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        mPresenter?.loadOrderInfo(mOrderId)
    }

    override fun initListener() {
        tv_order_button1.setOnClickListener(this)
        tv_order_button2.setOnClickListener(this)
        tv_order_button3.setOnClickListener(this)
        tv_order_button4.setOnClickListener(this)
    }

    private var mOrderDetailInfo: OrderDetailBean? = null
    override fun loadOrderDetailSuccess(info: OrderDetailBean) {
        showContent()
        mOrderDetailInfo = info
        mOrderStatus = info.STATUS
        invalidateOptionsMenu()
        setButtonStatus()

        mAdapter = OrderDetailAdapter(info.PRODUCT_LIST, this, mOrderStatus ?: 0)
        recycler_view_order_detail.layoutManager = LinearLayoutManager(this)
        recycler_view_order_detail.adapter = mAdapter

        val headerView = LayoutInflater.from(this).inflate(R.layout.item_order_detail_header, null)
        headerView.tv_delivery_name.text = info.RECEIVE_NAME
        headerView.tv_delivery_phone.text = info.RECEIVE_PHONE
        headerView.tv_delivery_card.text = "身份证号:${info.RECEIVE_IDENTITY}"
        headerView.tv_address.text = "${info.PROV} ${info.CITY} ${info.DIST} ${info.ADDRESS}"

        //订购人
        if (info.SUBSCRIBER_DATA == null){
            headerView.rl_order_people.visibility = View.GONE
        }else{
            headerView.rl_order_people.visibility = View.VISIBLE
            headerView.tv_order_people_name.text = info.SUBSCRIBER_DATA.TRUENAME
            headerView.tv_order_people_phone.text = info.SUBSCRIBER_DATA.PHONE
            headerView.tv_order_people_card.text = "身份证号:${info.SUBSCRIBER_DATA.IDENTITY}"
        }


        headerView.commodity_number.text = "共${info.PRODUCT_LIST.size}件商品"
        mAdapter?.addHeaderView(headerView)


        val footerView = LayoutInflater.from(this).inflate(R.layout.item_order_detail_footer, null)
        footerView.tv_commodity_price.text = "￥${info.TOTAL}"
        footerView.tv_commodity_freight.text = "+￥${info.POST_FEE}"
        footerView.tv_tax_fee.text = "+￥${info.TAX_FEE}"
        footerView.tv_discount_coupon.text = "-￥${info.PREFER_FEE}"
        if (info.STATUS == Order.PAYMENT) {
            footerView.actual_price.text = "订单应付金额："
            footerView.tv_actual_price.text = "￥${info.PAYABLE_FEE}"
        } else {
            footerView.actual_price.text = "订单实付金额："
            footerView.tv_actual_price.text = "￥${info.PAY_FEE}"
        }

//        footerView.tv_invoice_information.text = when (info.INVOICE_TYPE) {
//            1 -> "个人"
//            2 -> "单位"
//            else -> "不开票"
//        }
//
//        if (info.INVOICE_TYPE == 2) {
//            footerView.tv_invoice_title.visibility = View.VISIBLE
//            footerView.tv_invoice_tax_no.visibility = View.VISIBLE
//            footerView.tv_actual_price.text = "发票抬头：${info.INVOICE_HEAD}"
//            footerView.tv_invoice_tax_no.text = "公司税号：${info.INVOICE_NUM}"
//        } else {
//            footerView.tv_invoice_title.visibility = View.GONE
//            footerView.tv_invoice_tax_no.visibility = View.GONE
//        }

        footerView.tv_order_code.text = info.ORDER_NO

        footerView.tv_copy.setOnClickListener {
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // 将文本内容放到系统剪贴板里。
            cm.text = info.ORDER_NO
            toastShow(true, "复制成功~")
        }

        footerView.tv_orders_time.text = info.CREATETIME
        footerView.payment_time.text = "付款时间：${info.PAYTIME}"
        footerView.payment_method.text = "付款方式：${StringUtils.getPayStrByPayType(info.PAYWAY)}"
        footerView.tv_shipments_time.text = "发货时间：${info.SEND_TIME}"
        footerView.tv_delivery_time.text = "收货时间：${info.RECEIPT_TIME}"

        mAdapter?.addFooterView(footerView)

    }

    override fun loadOrderDetailFail(errMsg: String) {
        showError(errMsg)
    }

    /**
     * 根据不同状态按钮选择不一样
     */
    private fun setButtonStatus() {
        when (mOrderStatus) {
            Order.PAYMENT -> {
                layout_order_operation.visibility = View.VISIBLE

                tv_order_button1.text = "取消订单"
                tv_order_button4.text = "立即付款"

                ll_order_button4.visibility = View.VISIBLE
                ll_order_button1.visibility = View.VISIBLE
                ll_order_button2.visibility = View.GONE
                ll_order_button3.visibility = View.GONE
            }
            Order.WAIT_SEND -> {
                layout_order_operation.visibility = View.GONE

                ll_order_button4.visibility = View.GONE
                ll_order_button1.visibility = View.GONE
                ll_order_button3.visibility = View.GONE
                ll_order_button2.visibility = View.GONE
            }
            Order.WAIT_RECEIVE -> {
                layout_order_operation.visibility = View.VISIBLE

                tv_order_button1.text = "申请退款"
                tv_order_button4.text = "确认收货"
                tv_order_button2.text = "查看物流"

                ll_order_button4.visibility = View.VISIBLE
                ll_order_button1.visibility = View.VISIBLE
                ll_order_button2.visibility = View.VISIBLE
                ll_order_button3.visibility = View.GONE
            }
            Order.EVALUATE -> {
                layout_order_operation.visibility = View.VISIBLE

                tv_order_button1.text = "删除订单"
                tv_order_button2.text = "查看物流"
                tv_order_button3.text = "再次购买"
                tv_order_button4.text = "评价晒单"

                ll_order_button4.visibility = View.VISIBLE
                ll_order_button1.visibility = View.VISIBLE
                ll_order_button2.visibility = View.VISIBLE
                ll_order_button3.visibility = View.VISIBLE
            }
            Order.FINFISH -> {
                layout_order_operation.visibility = View.VISIBLE

                tv_order_button1.text = "删除订单"
                tv_order_button2.text = "查看物流"
                tv_order_button3.text = "再次购买"

                ll_order_button1.visibility = View.VISIBLE
                ll_order_button2.visibility = View.VISIBLE
                ll_order_button3.visibility = View.VISIBLE
                ll_order_button4.visibility = View.GONE
            }
        }
    }


    /**
     * 支付框
     */
    private fun initpayWayDialog() {
        val payWayDialog = PayWayDialog(this, R.style.recharge_pay_dialog)
        payWayDialog.show()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_order_button1 -> when (mOrderStatus) {
                Order.PAYMENT -> {
                    val builder = MessageDialog.Builder(mActivity)
                    builder.setTitle("确认要取消该订单吗？")
                            .setMessage("订单取消后不可恢复，需重新购买，请谨慎操作。")
                            .setCommit("确认取消", {
                                it.dismiss()
                                mPresenter?.cancelOrder(mOrderId)
                            })
                            .setCancel("暂不取消", {
                                it.dismiss()
                            }).show()
                }
//                Order.WAIT_RECEIVE -> startReturnActivity()
                Order.EVALUATE,
                Order.FINFISH -> {
                    val builder = MessageDialog.Builder(mActivity)
                    builder.setTitle("确认要删除该订单吗？")
                            .setMessage("订单删除后不可撤销，请谨慎操作。")
                            .setCommit("确认删除", {
                                it.dismiss()
                                mPresenter?.deleteOrder(mOrderId)
                            })
                            .setCancel("暂不删除", {
                                it.dismiss()
                            }).show()
                }
            }
            R.id.tv_order_button2 -> when (mOrderStatus) {
//                Order.WAIT_SEND -> startReturnActivity()
                Order.EVALUATE, Order.WAIT_RECEIVE, Order.FINFISH -> startLogisticsActivity(mOrderDetailInfo?.POST_INFO
                        ?: "")
            }
            R.id.tv_order_button3 -> {
                when (mOrderStatus) {
//                Order.WAIT_SEND -> startReturnActivity()
                    Order.EVALUATE, Order.FINFISH -> mPresenter?.buyAgain(mOrderId)
                }
            }
            R.id.tv_order_button4 -> when (mOrderStatus) {
                Order.PAYMENT -> initpayWayDialog()
                Order.WAIT_RECEIVE -> {
                    val builder = MessageDialog.Builder(mActivity)
                    builder.setTitle("确认要确认收货吗？")
                            .setMessage("请确保您已收到商品，确认收货后，不可撤销，请谨慎操作。")
                            .setCommit("确认收货", {
                                it.dismiss()
                                mPresenter?.receivedOrder(mOrderId)
                            })
                            .setCancel("暂不收货", {
                                it.dismiss()
                            }).show()
                }
                Order.EVALUATE -> {
                    EvaluateActivity.launch(mActivity, mOrderId)
                }
            }
        }
    }

    private fun startLogisticsActivity(expressUrl: String) {
        if (TextUtils.isEmpty(expressUrl)) return
        WebActivity.launch(mActivity, WebActivity.URL, expressUrl, "物流动态")
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        //如果是【已完成】 添加申请退货选择
//        return when (mOrderStatus) {
//            Order.EVALUATE -> {
//                menuInflater.inflate(R.menu.order_sales_return, menu)
//                true
//            }
//            else -> false
//        }
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        if (item != null) {
//            val id = item.itemId
//            if (id == R.id.order_sales_return) {
//                RequestReturnMoneyActivity.launch(mActivity,mOrderId,)
//                val intent = Intent(this, RequestReturnActivity::class.java)
//                startActivity(intent)
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

    override fun cancelOrderSuccess() {
        EventBus.getDefault().post(UpdateOrderEvent(intArrayOf(1)))
        toastShow(true, "取消成功")
        finish()
    }

    override fun cancelOrderFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun receivedOrderSuccess() {
        EventBus.getDefault().post(UpdateOrderEvent(intArrayOf(3, 4)))
        loadData()
    }

    override fun receivedOrderFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun deleteOrderSuccess() {
        EventBus.getDefault().post(UpdateOrderEvent(intArrayOf(4)))
        toastShow(true, "删除订单成功")
        finish()
    }

    override fun deleteOrderFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun buyAgainSuccess() {
        val builder = MessageDialog.Builder(mActivity)
        builder.setTitle("提示")
                .setMessage("商品已加入购物车")
                .setCommit("前往购物车", {
                    it.dismiss()
                    EventBus.getDefault().post(UpdateCartEvent())
                    MainActivity.launch(mActivity, "cart")
                })
                .setCancel("取消", {
                    it.dismiss()
                }).show()
    }

    override fun buyAgainFail(errMsg: String) {
        toastShow(errMsg)
    }


    override fun orderWechatSuccess(wechatInfo: WechatBean) {
        WeChatUtils.getInstance(mActivity).startPay(wechatInfo)
    }

    override fun orderAlipaySuccess(info: String) {
        AlipayUtils.getInstance().alipayByData(mActivity, info, this)
    }


    override fun orderBalanceSuccess() {
        onPaySuccess()
    }

    override fun onPaySuccess() {
        EventBus.getDefault().post(UpdateOrderEvent(intArrayOf(0, 1, 2)))
        toastShow(true, "支付成功")
        loadData()
    }

    override fun payFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun onPayWait() {
    }

    override fun onPayFail() {
        toastShow("支付失败")
    }


    @Subscribe
    fun onMainEvent(event: UpdateOrderDetailEvent) {
        loadData()
    }

    @Subscribe
    fun onMainEvent(event: PayResultEvent) {
        when {
            event.status == 0 -> onPaySuccess()
            event.status == 2 -> toastShow("已取消支付")
            else -> toastShow("支付失败")
        }
    }

    @Subscribe
    fun onMainEvent(event: PayRequestEvent) {
        when (event.payType) {
            ChoosePayTypeLayout.PayType.WECHAT -> {
                mPresenter?.wechat(mOrderId)
            }
            ChoosePayTypeLayout.PayType.ALIPAY -> {
                mPresenter?.alipay(mOrderId)
            }
            ChoosePayTypeLayout.PayType.BALANCE -> {
                mPresenter?.balance(mOrderId)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AlipayUtils.getInstance().release()
        WeChatUtils.getInstance(mActivity).release()
    }
}
