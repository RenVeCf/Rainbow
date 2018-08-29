package com.ipd.taxiu.ui.activity.balance

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.balance.BankCardFragment

/**
Created by Miss on 2018/8/10
我的银行卡
 */
class BankCardActivity : BaseUIActivity(){
    private val bankType:Int by lazy { intent.getIntExtra("bankType",0) }
    companion object {
        fun launch(activity:Activity,bankType:Int){
            val intent = Intent(activity,BankCardActivity::class.java)
            intent.putExtra("bankType",bankType)
            activity.startActivity(intent)
        }
    }
    override fun getContentLayout(): Int = R.layout.activity_container
    override fun getToolbarTitle(): String ="我的银行卡"

    override fun initView(bundle: Bundle?) {
       initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container,BankCardFragment.newInstance(bankType)).commit()
    }

    override fun initListener() {
    }
}