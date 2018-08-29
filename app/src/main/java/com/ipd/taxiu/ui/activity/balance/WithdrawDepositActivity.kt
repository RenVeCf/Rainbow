package com.ipd.taxiu.ui.activity.balance

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.widget.ApplyForDialog
import kotlinx.android.synthetic.main.activity_withdraw_deposit.*

/**
Created by Miss on 2018/8/10
余额提现
 */
class WithdrawDepositActivity : BaseUIActivity(), TextWatcher {
    companion object {
        val REQUEST_CODE = 10000 * 1
        fun launch(activity: Activity) {
            val intent = Intent(activity, WithdrawDepositActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_withdraw_deposit
    override fun getToolbarTitle(): String = "余额提现"

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {

    }

    override fun initListener() {
        et_money.addTextChangedListener(this)
        rl_choose_bank.setOnClickListener {
            val intent = Intent(this, BankCardActivity::class.java)
            intent.putExtra("bankType", MyBalanceActivity.CHOSSE_BANK_CARD)
            startActivityForResult(intent, REQUEST_CODE)
        }
        btn_confirm.setOnClickListener { initDialog() }
        btn_confirm.isClickable = false
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
        builder.setCommit("我知道了") { builder -> builder.dialog.dismiss() }
        builder.dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            var bankCard: String = data!!.getStringExtra("bankCard")
            tv_choose_bank.text = bankCard
        }
    }
}