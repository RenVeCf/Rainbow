package com.ipd.taxiu.ui.activity.balance

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.utils.BankCardUtils
import com.ipd.taxiu.widget.BankCardTextWatcher
import com.ipd.taxiu.widget.PickerUtil
import kotlinx.android.synthetic.main.activity_add_bank_card.*

/**
Created by Miss on 2018/8/10
添加银行卡
 */
class AddBankCardActivity : BaseUIActivity() {
    private var bankType: Int = 0
    private val list = arrayListOf<String>("中国银行",
            "中国建设银行",
            "中国农业银行",
            "中国工商银行",
            "中国交通银行")
    private val pickerUtil = PickerUtil()

    companion object {
        fun launch(activity: Activity, bankType: Int) {
            val intent = Intent(activity, AddBankCardActivity::class.java)
            intent.putExtra("bankType", bankType)
            activity.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_add_bank_card
    override fun getToolbarTitle(): String{
        bankType = intent.getIntExtra("bankType", 0)
        if (bankType == MyBalanceActivity.ADD_BANK_CARD) {
            return "添加银行卡"
        }
        return  "修改银行卡"
    }

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        BankCardTextWatcher.bind(et_bank_card_code)
    }

    override fun initListener() {
        tv_choose_bank_type.setOnClickListener { pickerUtil.initBankCardOption(this, list, tv_choose_bank_type) }
        btn_save.setOnClickListener {
            var bankCard:String = et_bank_card_code.text.toString().trim()
            if (!bankCard.isEmpty()) {
                bankCard = bankCard.replace(" ", "")
            }
            if (!BankCardUtils.checkBankCard(bankCard)){
                toastShow("请填写正确的银行卡号")
                return@setOnClickListener
            }
            if (bankType == MyBalanceActivity.ADD_BANK_CARD) {
                toastShow("添加成功")
            }
            if (bankType == MyBalanceActivity.UPDATE_BANK_CARD){
                toastShow("修改成功")
            }
            finish()
        }
    }
}