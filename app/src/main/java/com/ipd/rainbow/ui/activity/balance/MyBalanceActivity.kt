package com.ipd.rainbow.ui.activity.balance

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.BalanceResult
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.utils.HtmlImageGetter
import kotlinx.android.synthetic.main.activity_my_balance.*

/**
Created by Miss on 2018/8/10
我的余额
 */
class MyBalanceActivity : BaseUIActivity() {
    //1.添加银行卡 2.修改银行卡 3.选择银行卡
    companion object {
        val ADD_BANK_CARD = 1
        val UPDATE_BANK_CARD = 2
        val CHOSSE_BANK_CARD = 3

        fun launch(activity: Activity) {
            val intent = Intent(activity, MyBalanceActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_my_balance
    override fun getToolbarTitle(): String = "我的余额"

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        ApiManager.getService().getBalanceInfo(GlobalParam.getUserIdOrJump())
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BalanceResult>() {
                    override fun _onNext(result: BalanceResult) {
                        if (result.code == 0) {
                            tv_account_balance.text = "￥${result.data}"
                            tv_source.text = Html.fromHtml(result.info, HtmlImageGetter(mActivity, tv_source), null)

                        } else {
                            toastShow(result.msg)
                        }
                    }
                })
    }

    override fun initListener() {
        btn_account_balance.setOnClickListener { BalanceBillActivity.launch(this) }
        btn_withdraw_deposit.setOnClickListener { WithdrawDepositActivity.launch(this) }
        btn_my_bank_card.setOnClickListener { BankCardActivity.launch(this, ADD_BANK_CARD) }
    }
}