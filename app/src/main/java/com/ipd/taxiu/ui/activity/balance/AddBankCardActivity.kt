package com.ipd.taxiu.ui.activity.balance

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.widget.PickerUtil
import kotlinx.android.synthetic.main.activity_add_bank_card.*

/**
Created by Miss on 2018/8/10
添加银行卡
 */
class AddBankCardActivity : BaseUIActivity() {
    private val list = arrayListOf<String>("中国银行",
            "中国建设银行",
            "中国农业银行",
            "中国工商银行",
            "中国交通银行")
    private val pickerUtil = PickerUtil()

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, AddBankCardActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_add_bank_card
    override fun getToolbarTitle(): String = "添加银行卡"

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
        tv_choose_bank_type.setOnClickListener { pickerUtil.initBankCardOption(this,list,tv_choose_bank_type) }
        btn_save.setOnClickListener {
            toastShow("添加成功")
            finish()
        }
    }
}