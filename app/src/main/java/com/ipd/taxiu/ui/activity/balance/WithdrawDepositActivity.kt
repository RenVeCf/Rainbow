package com.ipd.taxiu.ui.activity.balance

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BankCardBean
import com.ipd.taxiu.bean.WithdrawHintBean
import com.ipd.taxiu.event.ChooseBankCardEvent
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.widget.ApplyForDialog
import kotlinx.android.synthetic.main.activity_withdraw_deposit.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
Created by Miss on 2018/8/10
余额提现
 */
class WithdrawDepositActivity : BaseUIActivity(), TextWatcher {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, WithdrawDepositActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_withdraw_deposit
    override fun getToolbarTitle(): String = "余额提现"

    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
    }

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        ApiManager.getService().balanceWithdrawHint(GlobalParam.getUserIdOrJump())
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<WithdrawHintBean>() {
                    override fun _onNext(result: WithdrawHintBean) {
                        if (result.code == 0) {
                            tv_withdraw_hint.text = String.format(resources.getString(R.string.withdraw_hint), result.count)
                            tv_account_balance.text = result.data
                        } else {
                            toastShow(result.msg)
                        }
                    }
                })

    }

    override fun initListener() {
        et_money.addTextChangedListener(this)
        rl_choose_bank.setOnClickListener {
            BankCardActivity.launch(mActivity, MyBalanceActivity.CHOSSE_BANK_CARD)
        }
        btn_confirm.isClickable = false
        btn_confirm.setOnClickListener {
            if (bankInfo == null) {
                toastShow("请选择提现银行")
                return@setOnClickListener
            }

            val money = et_money.text.toString().trim()
            if (TextUtils.isEmpty(money)) {
                toastShow("请输入提现金额")
                return@setOnClickListener
            }

            ApiManager.getService().balanceWithdraw(GlobalParam.getUserIdOrJump(), bankInfo?.BANK_CARD_ID.toString(), money)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<WithdrawHintBean>(mActivity, true) {
                        override fun _onNext(result: WithdrawHintBean) {
                            if (result.code == 0) {
                                initDialog()
                            } else {
                                toastShow(result.msg)
                            }
                        }
                    })


        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val balance = tv_account_balance.text.toString().toDouble()
        if (s.toString() != "") {
            if (s.toString().toDouble() in 100.0..balance) {
                btn_confirm.setBackgroundResource(R.drawable.shape_btn_enable)
                btn_confirm.isClickable = true
                btn_confirm.isClickable = true
            } else {
                btn_confirm.setBackgroundResource(R.drawable.shape_btn_disable)
                btn_confirm.isClickable = false
                btn_confirm.isClickable = false
            }
        }
    }

    fun initDialog() {
        val builder = ApplyForDialog.Builder(this)
        builder.setTitle("提现申请已提交")
        builder.setMessage("审核通过后，3-5个工作日到账")
        builder.setCommit("我知道了") { builder ->
            builder.dialog.dismiss()
            finish()
        }
        builder.dialog.show()
    }


    private var bankInfo: BankCardBean? = null
    @Subscribe
    fun onMainEvent(event: ChooseBankCardEvent) {
        bankInfo = event.bankInfo
        tv_choose_bank.text = event.bankInfo.BANK_NAME
    }


}