package com.ipd.taxiu.ui.activity.order

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.OrderDetailAdapter
import com.ipd.taxiu.bean.OrderDetailBean
import com.ipd.taxiu.presenter.order.OrderDetailPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.utils.Order
import com.ipd.taxiu.utils.StringUtils
import com.ipd.taxiu.widget.MessageDialog
import com.ipd.taxiu.widget.PayWayDialog
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.footer_order_button.*
import kotlinx.android.synthetic.main.item_order_detail_footer.view.*
import kotlinx.android.synthetic.main.item_order_detail_header.view.*


/**
 * Created by Miss on 2018/7/20
 * 订单详情
 */
class OrderDetailActivity : BaseUIActivity(), View.OnClickListener, OrderDetailPresenter.IOrderDetailView {

    companion object {
        fun launch(activity: Activity, orderId: Int) {
            val intent = Intent(activity, OrderDetailActivity::class.java)
            intent.putExtra("orderId", orderId)
            activity.startActivity(intent)
        }
    }


    private val mOrderId by lazy { intent.getIntExtra("orderId", -1) }
    private var mAdapter: OrderDetailAdapter? = null
    private var payWayDialog: PayWayDialog? = null
    private var mOrderStatus: Int? = null

    override fun getToolbarTitle() = "订单详情"

    override fun getContentLayout() = R.layout.activity_order_detail

    private var mPresenter: OrderDetailPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = OrderDetailPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
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

    override fun loadOrderDetailSuccess(info: OrderDetailBean) {
        showContent()
        mOrderStatus = info.STATUS
        invalidateOptionsMenu()
        setButtonStatus()

        mAdapter = OrderDetailAdapter(info.PRODUCT_LIST, this, mOrderStatus ?: 0)
        recycler_view_order_detail.layoutManager = LinearLayoutManager(this)
        recycler_view_order_detail.adapter = mAdapter

        val headerView = LayoutInflater.from(this).inflate(R.layout.item_order_detail_header, null)
        headerView.tv_delivery_name.text = info.RECEIVE_NAME
        headerView.tv_delivery_phone.text = info.RECEIVE_PHONE
        headerView.tv_address.text = "${info.PROV} ${info.CITY} ${info.DIST} ${info.ADDRESS}"
        headerView.commodity_number.text = "共${info.PRODUCT_NUM}件商品"
        mAdapter?.addHeaderView(headerView)


        val footerView = LayoutInflater.from(this).inflate(R.layout.item_order_detail_footer, null)
        footerView.tv_commodity_price.text = "￥${info.TOTAL}"
        footerView.tv_commodity_freight.text = "+￥${info.POST_FEE}"
        footerView.tv_discount_coupon.text = "-￥${info.PREFER_FEE}"
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
                tv_order_button1.text = "取消订单"
                tv_order_button4.text = "立即付款"

                ll_order_button4.visibility = View.VISIBLE
                ll_order_button1.visibility = View.VISIBLE
                ll_order_button2.visibility = View.GONE
                ll_order_button3.visibility = View.GONE
            }
            Order.WAIT_SEND -> {
                tv_order_button2.text = "申请退款"

                ll_order_button4.visibility = View.GONE
                ll_order_button1.visibility = View.GONE
                ll_order_button3.visibility = View.GONE
                ll_order_button2.visibility = View.VISIBLE
            }
            Order.WAIT_RECEIVE -> {
                tv_order_button1.text = "申请退款"
                tv_order_button4.text = "确认收货"
                tv_order_button2.text = "查看物流"

                ll_order_button4.visibility = View.VISIBLE
                ll_order_button1.visibility = View.VISIBLE
                ll_order_button2.visibility = View.VISIBLE
                ll_order_button3.visibility = View.GONE
            }
            Order.EVALUATE -> {
                tv_order_button1.text = "删除订单"
                tv_order_button2.text = "查看物流"
                tv_order_button3.text = "再次购买"
                tv_order_button4.text = "评价晒单"

                ll_order_button4.visibility = View.VISIBLE
                ll_order_button1.visibility = View.VISIBLE
                ll_order_button2.visibility = View.VISIBLE
                ll_order_button3.visibility = View.VISIBLE
            }
        }
    }


    /**
     * 支付框
     */
    private fun initpayWayDialog() {
        payWayDialog = PayWayDialog(this, R.style.recharge_pay_dialog)
        payWayDialog?.show()
    }

    /**
     * 取消订单提示框
     */
    private fun initMessageDialog(title: String, message: String, commitStr: String, cancelStr: String, toastMsg: String) {
        val builder = MessageDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCommit(commitStr) { builder ->
            toastShow(toastMsg)
            builder.dialog.dismiss()
            finish()
        }
        builder.setCancel(cancelStr) { builder -> builder.dialog.dismiss() }
        builder.dialog.show()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_order_button1 -> when (mOrderStatus) {
                Order.PAYMENT -> initMessageDialog("确认要取消该订单吗？",
                        "订单取消后不可恢复，需重新购买，请谨慎操作。",
                        "确认取消",
                        "暂不取消",
                        "取消成功")
                Order.WAIT_RECEIVE -> startReturnActivity()
                Order.EVALUATE -> initMessageDialog("确认要删除该订单吗？",
                        "订单删除后不可撤销，请谨慎操作。",
                        "确认删除",
                        "暂不删除",
                        "删除成功")
            }
            R.id.tv_order_button2 -> when (mOrderStatus) {
                Order.WAIT_SEND -> startReturnActivity()
                Order.EVALUATE -> startLogisticsActivity()
                Order.WAIT_RECEIVE -> startLogisticsActivity()
            }
            R.id.tv_order_button3 -> {
            }
            R.id.tv_order_button4 -> when (mOrderStatus) {
                Order.PAYMENT -> initpayWayDialog()
                Order.WAIT_RECEIVE -> initMessageDialog("确认要确认收货吗？",
                        "请确保您已收到商品，确认收货后，不可撤销，请谨慎操作。",
                        "确认收货",
                        "暂不收货",
                        "收货成功")
                Order.EVALUATE -> {
                    val intent = Intent(this, EvaluateActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun startReturnActivity() {
        val intent = Intent(this, RequestReturnMoneyActivity::class.java)
        startActivity(intent)
    }

    private fun startLogisticsActivity() {
        val intent = Intent(this, LogisticsDetailActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //如果是【已完成】 添加申请退货选择
        return when (mOrderStatus) {
            Order.EVALUATE -> {
                menuInflater.inflate(R.menu.order_sales_return, menu)
                true
            }
            else -> false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            val id = item.itemId
            if (id == R.id.order_sales_return) {
                val intent = Intent(this, RequestReturnActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
