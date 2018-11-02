package com.ipd.taxiu.ui.activity.trade

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ConfirmOrderProductAdapter
import com.ipd.taxiu.bean.*
import com.ipd.taxiu.event.ChooseAddressEvent
import com.ipd.taxiu.event.UpdateCartEvent
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.presenter.store.ConfirmOrderPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.activity.address.DeliveryAddressActivity
import com.ipd.taxiu.utils.AlipayUtils
import com.ipd.taxiu.utils.WeChatUtils
import com.ipd.taxiu.widget.ChoosePayTypeLayout
import com.ipd.taxiu.widget.ProductCouponDialog
import kotlinx.android.synthetic.main.activity_confirm_order.*
import kotlinx.android.synthetic.main.layout_choose_address.*
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

        fun launch(context: Context, activityId: Int, productId: Int, formId: Int, num: Int, type: Int = SPELL) {
            val intent = Intent(context, ConfirmOrderActivity::class.java)
            intent.putExtra("isCart", 0)
            intent.putExtra("activityId", activityId)
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
            mPresenter?.cartCash(mCartIds, mIsCart, mNum, mProductId, mFormId,mIsGroup)
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
                mPresenter?.confirmOrder(mCartIds, mIsCart, mNum, mProductId, mFormId, mAddressInfo!!.ADDRESS_ID, companyHeader, companyTaxNo, invoiceType, payType, 0, 0)
            } else if (mType == SPELL) {
                mPresenter?.spellConfirmOrder(mActivityId, mNum, mProductId, mFormId, mAddressInfo!!.ADDRESS_ID, companyHeader, companyTaxNo, invoiceType, payType, 0, 0)
            }
        }
        cv_address.setOnClickListener {
            DeliveryAddressActivity.launch(this, DeliveryAddressActivity.CHOOSE)
        }
        ll_coupon.setOnClickListener {
            //优惠券
            ApiManager.getService().cartCoupon(GlobalParam.getUserIdOrJump(), mCartIds, mIsCart, mNum, mProductId, mFormId)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<List<ExchangeBean>>>(mActivity, true) {
                        override fun _onNext(result: BaseResult<List<ExchangeBean>>) {
                            if (result.code == 0) {
                                val couponDialog = ProductCouponDialog(mActivity)
                                couponDialog.setData(result.data)
                                couponDialog.show()
                            } else {
                                toastShow(result.msg)
                            }
                        }
                    })
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

    override fun loadCartCashSuccess(info: CartCashBean) {
        tv_product_info.text = "共${info.PRODUCT_NUM}件商品"
        tv_total_product_price.text = "￥${info.PRODUCT_TOTAL}"
        tv_express_fee.text = "+￥${info.POST_FEE}"
        tv_wallet_balance.text = "余额：￥${info.BALANCE}"
        tv_actual_price.text = "￥${info.PAY_FEE}"
        tv_coupon_deduction.text = "￥${info.PREFER_FEE}"

        setAddressInfo(info.ADDRESS_DATA)
        setCouponInfo(info.EXCHANGE_DATA)

        //提示
        if (TextUtils.isEmpty(info.COUPON_TIP)) {
            rl_hint.visibility = View.GONE
        } else {
            rl_hint.visibility = View.VISIBLE
            tv_hint.text = info.COUPON_TIP
            iv_hint_close.setOnClickListener { rl_hint.visibility = View.GONE }
        }

        product_recycler_view.adapter = ConfirmOrderProductAdapter(mActivity, info.PRODUCT_LIST, {

        })

        showContent()

    }

    private fun setCouponInfo(couponInfo: ExchangeBean?) {
        if (couponInfo != null && couponInfo!!.EXCHANGE_ID != 0) {
            tv_coupon_use.setTextColor(resources.getColor(R.color.red))
            tv_coupon_use.text = "满${couponInfo.SATISFY_PRICE}减${couponInfo.PRICE}"
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
            tv_receive_address.text = "${addressInfo.PROV} ${addressInfo.CITY} ${addressInfo.DIST} ${addressInfo.ADDRESS}"
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
                        onPaySuccess()
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

    override fun onDestroy() {
        super.onDestroy()
        AlipayUtils.getInstance().release()
        WeChatUtils.getInstance(mActivity).release()
    }

}
