package com.ipd.taxiu.ui.activity.balance

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
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
    }

    override fun initListener() {
        btn_account_balance.setOnClickListener { BalanceBillActivity.launch(this) }
        btn_withdraw_deposit.setOnClickListener { WithdrawDepositActivity.launch(this) }
        btn_my_bank_card.setOnClickListener { BankCardActivity.launch(this, ADD_BANK_CARD) }
    }
}