package com.ipd.rainbow.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.*
import com.ipd.rainbow.event.PayRequestEvent
import com.ipd.rainbow.event.UpdateUserInfoEvent
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.presenter.VipPresenter
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.activity.referral.ReferralCodeActivity
import com.ipd.rainbow.utils.AlipayUtils
import com.ipd.rainbow.utils.WeChatUtils
import com.ipd.rainbow.utils.WebUtils
import com.ipd.rainbow.widget.ChoosePayTypeLayout
import com.ipd.rainbow.widget.MessageDialog
import com.ipd.rainbow.widget.PayWayDialog
import kotlinx.android.synthetic.main.activity_vip.*
import kotlinx.android.synthetic.main.layout_vip_recharge.view.*
import kotlinx.android.synthetic.main.layout_vip_recharge_type.view.*
import kotlinx.android.synthetic.main.vip_toolbar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable
import rx.Subscriber


class VipActivity : BaseUIActivity(), VipPresenter.IVipView {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, VipActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarLayout(): Int = R.layout.vip_toolbar

    override fun getToolbarTitle(): String = "彩虹会员"

    override fun getContentLayout(): Int = R.layout.activity_vip

    private var mPresenter: VipPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
        mPresenter = VipPresenter()
        mPresenter?.attachView(mActivity, this)
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

        Observable.zip(ApiManager.getService().getVipInfo(GlobalParam.getUserIdOrJump(), "0"),
                ApiManager.getService().getVipPriceInfo(GlobalParam.getUserIdOrJump(), "1")
        ) { t1, t2 ->
            if (t1.code == 0 && t2.code == 0) {
                t1.data.VIP_LIST.forEach { t1Info ->
                    t2.data.forEach { t2Info ->
                        if (t1Info.LEVEL == t2Info.LEVEL) {
                            t1Info.AUTO_PAY_LIST = t2Info.LIST
                        }
                    }
                }

                t1
            } else {
                t1.code = if (t1.code == 0) t2.code else t1.code
                t1.msg = if (t1.code == 0) t2.msg else t1.msg
                t1
            }
        }.compose(RxScheduler.applyScheduler())
                .subscribe(object : Subscriber<BaseResult<VipInfoBean>>() {
                    override fun onNext(result: BaseResult<VipInfoBean>) {
                        if (result.code == 0) {
                            setContent(result.data)
                            showContent()
                        } else {
                            showError(result.msg)
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        showError()
                    }

                })

//        ApiManager.getService().getVipInfo(GlobalParam.getUserIdOrJump(), "0")
//                .compose(RxScheduler.applyScheduler())
//                .subscribe(object : Response<BaseResult<VipInfoBean>>() {
//                    override fun _onNext(result: BaseResult<VipInfoBean>) {
//                        if (result.code == 0) {
//                            setContent(result.data)
//                            showContent()
//                        } else {
//                            showError(result.msg)
//                        }
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        showError()
//                    }
//                })
    }

    private var mPayInfo: VipPayInfo? = null
    private fun setContent(info: VipInfoBean) {
        ImageLoader.loadAvatar(mActivity, info.LOGO, civ_avatar)
        tv_nickname.text = info.NICKNAME

        var levelStr = "普通用户"
        var pos = 0
        info.VIP_LIST.forEachIndexed { index, viplistBean ->
            if (viplistBean.LEVEL == info.LEVEL) {
                levelStr = viplistBean.LEVEL_NAME
                pos = index
                return@forEachIndexed
            }
        }

        tv_level.text = levelStr
        tv_level_desc.text = if (levelStr == "普通用户") "您还不是彩虹会员" else "${levelStr}有效期：${info.END_TIME}"

        iv_vip_ewm.visibility = if (levelStr == "普通用户") View.GONE else View.VISIBLE
        iv_vip_ewm.setOnClickListener {
            //二维码
            ReferralCodeActivity.launch(mActivity)
        }


        //自动续费
        tv_auto_pay.visibility = if (info.AUTO_PAY == 1) View.VISIBLE else View.GONE
        tv_auto_pay.setOnClickListener {
            val builder = MessageDialog.Builder(mActivity)
            builder.setTitle("功能提醒")
                    .setMessage("请确认是否关闭自动续费功能")
                    .setCommit("确认") {
                        it.dismiss()
                        mPresenter?.closeAutoPay()
                    }
                    .setCancel("取消") {
                        it.dismiss()
                    }.show()
        }

        view_pager.adapter = object : PagerAdapter() {
            private val mInflater by lazy { LayoutInflater.from(mActivity) }
            override fun isViewFromObject(view: View?, `object`: Any?): Boolean = view == `object`

            override fun getCount(): Int = info.VIP_LIST.size

            private fun setPriceInfo(contentView: View, info: VipInfoBean.VipLevelBean, isAutoPay: Boolean) {
                contentView.single_choice_layout.removeAllViews()

                val list = if (isAutoPay) info.AUTO_PAY_LIST else info.LIST
                list.forEachIndexed { index, vipDesc ->
                    if (index >= 3) return@forEachIndexed
                    val typeView = mInflater.inflate(R.layout.layout_vip_recharge_type, contentView.single_choice_layout, false)
                    typeView.tv_type_name.text = vipDesc.TYPE_NAME
                    typeView.tv_type_price.text = vipDesc.CURRENT_PRICE
                    typeView.tv_type_desc.text = vipDesc.CONTENT
                    if (!isAutoPay){
                        typeView.tv_type_sub_price.text = "每月仅￥${vipDesc.CURRENT_PRICE}"
                    }else{
                        typeView.tv_type_sub_price.text = if (index == 0)"每月仅￥${vipDesc.CURRENT_PRICE}" else "原价￥${vipDesc.PRICE}"
                    }
                    contentView.single_choice_layout.addView(typeView)
                }
            }

            override fun instantiateItem(container: ViewGroup?, position: Int): Any {
                val vipInfo = info.VIP_LIST[position]

                val contentView = mInflater.inflate(R.layout.layout_vip_recharge, container, false)

                setPriceInfo(contentView, vipInfo, contentView.cb_auto_pay.isChecked)
                contentView.cb_auto_pay.setOnCheckedChangeListener { buttonView, isChecked ->
                    setPriceInfo(contentView, vipInfo, isChecked)
                }


                //特权介绍
                contentView.tv_vip_info.text = "${vipInfo.LEVEL_NAME}专属特权"
                contentView.wv_vip_info.loadData(WebUtils.getHtmlData(vipInfo.VIP_EXCLUSIVE), "text/html; charset=UTF-8", null)
                //资费说明
                contentView.tv_fee_info.text = "${vipInfo.LEVEL_NAME}资费说明"
                contentView.wv_vip_fee.loadData(WebUtils.getHtmlData(vipInfo.VIP_DESC), "text/html; charset=UTF-8", null)

                //操作按钮
                contentView.btn_register.isEnabled = info.LEVEL <= vipInfo.LEVEL
                contentView.btn_register.text = if (info.LEVEL > vipInfo.LEVEL) "已开通" else if (info.LEVEL == vipInfo.LEVEL) "立即续费" else "立即升级"


                contentView.btn_register.setOnClickListener {
                    val checkedPos = contentView.single_choice_layout.getCheckedPos()
                    if (vipInfo.LIST == null || vipInfo.LIST.isEmpty()) {
                        toastShow("请选择购买类型")
                        return@setOnClickListener
                    }


                    val type = vipInfo.LIST[checkedPos].TYPE
                    val isAutoPay = contentView.cb_auto_pay.isChecked

                    mPayInfo = VipPayInfo(vipInfo.LEVEL, type, if (isAutoPay) 1 else 0)

                    PayWayDialog(mActivity, R.style.recharge_pay_dialog).show()
                }


                container?.addView(contentView)
                return contentView
            }

            override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
                container?.removeView(`object` as View?)
            }

        }

        val titles = info.VIP_LIST.map { it.LEVEL_NAME }
        tab_layout.setViewPager(view_pager, titles.toTypedArray())
        view_pager.currentItem = pos
    }

    override fun initListener() {

    }


    @Subscribe
    fun onMainEvent(event: PayRequestEvent) {
        if (mPayInfo == null) return
        mPresenter?.confirmOrder(mPayInfo!!.level, mPayInfo!!.type, mPayInfo!!.isAutoPay, event.payType)

    }

    override fun confirmOrderSuccess(payType: Int, payResult: PayResult<String>?, wechatPayResult: PayResult<WechatBean>?) {
        when (payType) {
            ChoosePayTypeLayout.PayType.ALIPAY -> {
                AlipayUtils.getInstance().alipayByData(mActivity, payResult?.info, object : AlipayUtils.OnPayListener {
                    override fun onPaySuccess() {
                        this@VipActivity.onPaySuccess()
                    }

                    override fun onPayWait() {
                    }

                    override fun onPayFail() {
                        this@VipActivity.confirmOrderFail("支付失败")
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
        mPayInfo = null
        EventBus.getDefault().post(UpdateUserInfoEvent())
        toastShow(true, "支付成功")
        loadData()
    }


    override fun confirmOrderFail(errMsg: String) {
        mPayInfo = null
        toastShow(errMsg)
    }

    override fun closeAutoPaySuccess() {
        toastShow(true, "关闭成功")
        tv_auto_pay.visibility = View.GONE

    }

    override fun closeAutoPayFail(errMsg: String) {
        toastShow(errMsg)
    }


    override fun onDestroy() {
        super.onDestroy()
        AlipayUtils.getInstance().release()
        WeChatUtils.getInstance(mActivity).release()
    }


}