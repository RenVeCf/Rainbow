package com.ipd.rainbow.ui.activity.trade

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.ConfirmOrderProductAdapter
import com.ipd.rainbow.bean.*
import com.ipd.rainbow.event.*
import com.ipd.rainbow.presenter.store.ConfirmOrderPresenter
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.activity.address.DeliveryAddressActivity
import com.ipd.rainbow.ui.activity.address.OrderPeopleActivity
import com.ipd.rainbow.utils.AlipayUtils
import com.ipd.rainbow.utils.WeChatUtils
import com.ipd.rainbow.widget.ChoosePayTypeLayout
import kotlinx.android.synthetic.main.activity_confirm_order.*
import kotlinx.android.synthetic.main.layout_choose_address.*
import kotlinx.android.synthetic.main.layout_choose_order_people.*
import kotlinx.android.synthetic.main.layout_confirm_order_other_info.*
import kotlinx.android.synthetic.main.layout_order_product.*
import kotlinx.android.synthetic.main.layout_pay_type.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class ConfirmOrderActivity : BaseUIActivity(), ConfirmOrderPresenter.IConfirmOrderView {

    companion object {
        val NORMAL = 1
        val SPELL = 2
        fun launch(activity: Activity, cartIds: String) {
            val intent = Intent(activity, ConfirmOrderActivity::class.java)
            intent.putExtra("isCart", 1)
            intent.putExtra("cartIds", cartIds)
            intent.putExtra("isGroup", false)
            activity.startActivity(intent)
        }

        fun launch(context: Context, productId: Int, formId: Int, num: Int, isGroup: Boolean, type: Int = NORMAL) {
            val intent = Intent(context, ConfirmOrderActivity::class.java)
            intent.putExtra("isCart", 0)
            intent.putExtra("productId", productId)
            intent.putExtra("formId", formId)
            intent.putExtra("num", num)
            intent.putExtra("type", type)
            intent.putExtra("isGroup", isGroup)
            context.startActivity(intent)
        }

        fun launch(context: Context, productId: Int, formId: Int, num: Int, type: Int = SPELL) {
            val intent = Intent(context, ConfirmOrderActivity::class.java)
            intent.putExtra("isCart", 0)
            intent.putExtra("productId", productId)
            intent.putExtra("formId", formId)
            intent.putExtra("num", num)
            intent.putExtra("type", type)
            intent.putExtra("isGroup", false)
            context.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "确认订单"
    override fun getContentLayout(): Int = R.layout.activity_confirm_order


    private var mPresenter: ConfirmOrderPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
        mPresenter = ConfirmOrderPresenter()
        mPresenter?.attachView(mActivity, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
        mPresenter?.detachView()
        mPresenter = null
    }


    private val mType: Int by lazy { intent.getIntExtra("type", NORMAL) }
    private val mIsCart: Int by lazy { intent.getIntExtra("isCart", 0) }
    private val mProductId: Int by lazy { intent.getIntExtra("productId", 0) }
    private val mActivityId: Int by lazy { intent.getIntExtra("activityId", 0) }
    private val mFormId: Int by lazy { intent.getIntExtra("formId", 0) }
    private val mNum: Int by lazy { intent.getIntExtra("num", 0) }
    private val mCartIds: String by lazy { intent.getStringExtra("cartIds") ?: "" }
    private val mIsGroup: Boolean by lazy { intent.getBooleanExtra("isGroup", false) }
    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        if (mType == NORMAL) {
            mPresenter?.cartCash(mCartIds, mIsCart, mNum, mProductId, mFormId, mIsGroup)
        } else {
            mPresenter?.spellCash(mActivityId, mNum, mProductId, mFormId)
        }
    }

    override fun initListener() {
        tv_confirm.setOnClickListener {
            //支付
            //收货地址
            if (mAddressInfo == null) {
                toastShow("请选择收货地址")
                return@setOnClickListener
            }
            if (mOrderPeopleInfo == null) {
                toastShow("请选择订购人信息")
                return@setOnClickListener
            }
            //发票
            var companyHeader = ""
            var companyTaxNo = ""
            if (rg_invoice.checkedRadioButtonId == R.id.rb_company) {
                companyHeader = et_company_header.text.toString().trim()
                companyTaxNo = et_company_tax_no.text.toString().trim()
                if (TextUtils.isEmpty(companyHeader)) {
                    toastShow("请输入单位发票抬头")
                    return@setOnClickListener
                } else if (TextUtils.isEmpty(companyTaxNo)) {
                    toastShow("请输入单位税号")
                    return@setOnClickListener
                }
            }
            val invoiceType = when (rg_invoice.checkedRadioButtonId) {
                R.id.rb_company -> 2
                R.id.rb_person -> 1
                else -> 0
            }

            //支付方式
            val payType = choose_pay_type_layout.getPayType()

            if (mType == NORMAL) {
                mPresenter?.confirmOrder(mCartIds, mIsCart, mNum, mProductId, mFormId, mAddressInfo!!.ADDRESS_ID,mOrderPeopleInfo!!.SUBSCRIBER_ID, companyHeader, companyTaxNo, invoiceType, payType, mUseCoupon, mCouponId)
            } else if (mType == SPELL) {
                mPresenter?.spellConfirmOrder(mActivityId, mNum, mProductId, mFormId, mAddressInfo!!.ADDRESS_ID,mOrderPeopleInfo!!.SUBSCRIBER_ID, companyHeader, companyTaxNo, invoiceType, payType, mUseCoupon, mCouponId)
            }
        }
        cv_address.setOnClickListener {
            DeliveryAddressActivity.launch(this, DeliveryAddressActivity.CHOOSE)
        }
        cv_order_people.setOnClickListener {
            OrderPeopleActivity.launch(this, OrderPeopleActivity.CHOOSE)
        }
        ll_coupon.setOnClickListener {
            //优惠券
            CartCouponActivity.launch(mActivity, mCartIds, mIsCart, mNum, mProductId, mFormId)
//            ApiManager.getService().cartCoupon(GlobalParam.getUserIdOrJump(), mCartIds, mIsCart, mNum, mProductId, mFormId)
//                    .compose(RxScheduler.applyScheduler())
//                    .subscribe(object : Response<BaseResult<List<ExchangeBean>>>(mActivity, true) {
//                        override fun _onNext(result: BaseResult<List<ExchangeBean>>) {
//                            if (result.code == 0) {
//                                val couponDialog = ProductCouponDialog(mActivity)
//                                couponDialog.setData(result.data)
//                                couponDialog.show()
//                            } else {
//                                toastShow(result.msg)
//                            }
//                        }
//                    })

        }

        rg_invoice.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_without, R.id.rb_person -> {
                    et_company_header.visibility = View.GONE
                    et_company_tax_no.visibility = View.GONE
                }
                R.id.rb_company -> {
                    et_company_header.visibility = View.VISIBLE
                    et_company_tax_no.visibility = View.VISIBLE
                }
            }

        }

    }

    private var mCartInfo: CartCashBean? = null
    override fun loadCartCashSuccess(info: CartCashBean) {
        mCartInfo = info
        tv_product_info.text = "共${info.PRODUCT_NUM}件商品"
        tv_total_product_price.text = "￥${info.PRODUCT_TOTAL}"
        tv_express_fee.text = "+￥${info.POST_FEE}"
        tv_tax_fee.text = "+￥${info.TAX_FEE}"
        tv_wallet_balance.text = "余额：￥${info.BALANCE}"
        tv_actual_price.text = "￥${info.PAY_FEE}"
        tv_coupon_deduction.text = "￥${info.PREFER_FEE}"

        setAddressInfo(info.ADDRESS_DATA)
        setOrderPeopleInfo(info.SUBSCRIBER_DATA)
        setCouponInfo(couponInfo = info.EXCHANGE_DATA)

        //提示
        if (TextUtils.isEmpty(info.COUPON_TIP)) {
            rl_hint.visibility = View.GONE
        } else {
            rl_hint.visibility = View.VISIBLE
            tv_hint.text = info.COUPON_TIP
            iv_hint_close.setOnClickListener { rl_hint.visibility = View.GONE }
        }

        product_recycler_view.adapter = ConfirmOrderProductAdapter(mActivity, info.PRODUCT_LIST) {

        }

        showContent()
    }

    private var mUseCoupon = 0
    private var mCouponId = 0
    private fun setCouponInfo(type: Int = 0, couponInfo: ExchangeBean?) {
        if (type == 0) {
            mUseCoupon = 0
            mCouponId = 0
            if (couponInfo != null && couponInfo!!.EXCHANGE_ID != 0) {
                tv_coupon_use.setTextColor(resources.getColor(R.color.red))
                tv_coupon_use.text = "满${couponInfo.SATISFY_PRICE}减${couponInfo.PRICE}"
            } else {
                tv_coupon_use.setTextColor(resources.getColor(R.color.LightGrey))
                tv_coupon_use.text = "无优惠券可用"
            }
        } else {
            if (couponInfo != null) {
                mUseCoupon = 2
                mCouponId = couponInfo.EXCHANGE_ID
                tv_coupon_use.setTextColor(resources.getColor(R.color.red))
                tv_coupon_use.text = "满${couponInfo.SATISFY_PRICE}减${couponInfo.PRICE}"

                tv_actual_price.text = "￥${CommonUtils.beautifulDouble((mCartInfo?.PRODUCT_TOTAL
                        ?: "0").toDouble().plus((mCartInfo?.POST_FEE
                        ?: "0").toDouble().minus(couponInfo.PRICE.toDouble())))}"
                tv_coupon_deduction.text = "￥${couponInfo.PRICE}"
            } else {
                mUseCoupon = 1
                mCouponId = 0
                tv_coupon_use.setTextColor(resources.getColor(R.color.LightGrey))
                tv_coupon_use.text = "不使用优惠券"

                tv_actual_price.text = "￥${CommonUtils.beautifulDouble((mCartInfo?.PRODUCT_TOTAL
                        ?: "0").toDouble().plus((mCartInfo?.POST_FEE ?: "0").toDouble()))}"
                tv_coupon_deduction.text = "￥0.00"
            }

        }
    }

    private var mAddressInfo: AddressBean? = null
    private fun setAddressInfo(addressInfo: AddressBean?) {
        if (addressInfo?.ADDRESS_ID == null) {
            cl_has_not_address.visibility = View.VISIBLE
            cl_has_address.visibility = View.GONE
        } else {
            mAddressInfo = addressInfo
            cl_has_not_address.visibility = View.GONE
            cl_has_address.visibility = View.VISIBLE
            tv_receiver_info.text = "${addressInfo.RECIPIENT}    ${addressInfo.TEL}"
            tv_receiver_card.text = "身份证号:${addressInfo.IDENTITY}"
            tv_receive_address.text = "${addressInfo.PROV} ${addressInfo.CITY} ${addressInfo.DIST} ${addressInfo.ADDRESS}"
        }
    }


    private var mOrderPeopleInfo: OrderPeopleBean? = null
    private fun setOrderPeopleInfo(orderPeopleInfo: OrderPeopleBean?) {
        if (orderPeopleInfo?.SUBSCRIBER_ID == null) {
            cl_has_order_people.visibility = View.GONE
            cl_has_not_order_people.visibility = View.VISIBLE

        } else {
            mOrderPeopleInfo = orderPeopleInfo

            cl_has_order_people.visibility = View.VISIBLE
            cl_has_not_order_people.visibility = View.GONE
            tv_order_people_info.text = orderPeopleInfo.TRUENAME + "    " + CommonUtils.getEncryPhone(orderPeopleInfo.PHONE)
            tv_order_people_card.text = "身份证号:" + orderPeopleInfo.IDENTITY

        }
    }


    override fun loadCartCashFail(errMsg: String) {
        showError(errMsg)
    }

    override fun confirmOrderSuccess(payType: Int, payResult: PayResult<String>?, wechatPayResult: PayResult<WechatBean>?) {
        when (payType) {
            ChoosePayTypeLayout.PayType.ALIPAY -> {
                AlipayUtils.getInstance().alipayByData(mActivity, payResult?.info, object : AlipayUtils.OnPayListener {
                    override fun onPaySuccess() {
                        this@ConfirmOrderActivity.onPaySuccess()
                    }

                    override fun onPayWait() {
                    }

                    override fun onPayFail() {
                        toastShow("支付失败")
                    }

                })

            }
            ChoosePayTypeLayout.PayType.WECHAT -> {
                WeChatUtils.getInstance(mActivity).startPay(wechatPayResult?.info)
            }
            ChoosePayTypeLayout.PayType.BALANCE -> {
                onPaySuccess()
            }
        }

    }

    private fun onPaySuccess() {
        EventBus.getDefault().post(UpdateCartEvent())
        toastShow(true, "支付成功")
        finish()
    }

    override fun confirmOrderFail(errMsg: String) {
        toastShow(errMsg)
    }


    @Subscribe
    fun onMainEvent(event: ChooseAddressEvent) {
        setAddressInfo(event.addressInfo)
    }

    @Subscribe
    fun onMainEvent(event: ChooseOrderPeopleEvent) {
        setOrderPeopleInfo(event.addressInfo)
    }

    @Subscribe
    fun onMainEvent(event: ChooseCouponEvent) {
        setCouponInfo(1, event.couponInfo)
    }

    @Subscribe
    fun onMainEvent(event: PayResultEvent) {
        when (event.status) {
            0 -> onPaySuccess()
            1 -> toastShow("支付失败")
            2 -> toastShow("取消支付")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AlipayUtils.getInstance().release()
        WeChatUtils.getInstance(mActivity).release()
    }

}
