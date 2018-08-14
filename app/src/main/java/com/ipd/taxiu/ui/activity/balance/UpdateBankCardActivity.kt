package com.ipd.taxiu.ui.activity.balance

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.widget.MessageDialog
import com.ipd.taxiu.widget.PickerUtil
import kotlinx.android.synthetic.main.activity_add_bank_card.*

/**
Created by Miss on 2018/8/10
修改银行卡
 */
class UpdateBankCardActivity:BaseUIActivity() {
    private val pickerUtil = PickerUtil()
    private val list = arrayListOf<String>("中国银行",
            "中国建设银行",
            "中国农业银行",
            "中国工商银行",
            "中国交通银行")
    companion object {
        fun launch(activity:Activity){
            val intent = Intent(activity,UpdateBankCardActivity::class.java)
            activity.startActivity(intent)
        }
    }
    override fun getContentLayout(): Int = R.layout.activity_update_bank_card
    override fun getToolbarTitle(): String = "修改银行卡"

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
        tv_choose_bank_type.setOnClickListener { pickerUtil.initBankCardOption(this,list,tv_choose_bank_type) }
        btn_save.setOnClickListener {
            toastShow("修改成功")
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete_address,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id:Int = item!!.itemId
        if (id == R.id.delete_address){
            initMessageDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    fun initMessageDialog(){
        val builder = MessageDialog.Builder(this)
        builder.setTitle("确认要删除该银行卡吗？")
        builder.setMessage("删除后不可恢复，请谨慎操作")
        builder.setCommit("确认删除") { builder ->
            toastShow(true, "删除成功")
            builder.dialog.dismiss()
            finish()
        }
        builder.setCancel("暂不删除") { builder -> builder.dialog.dismiss() }
        builder.dialog.show()
    }
}