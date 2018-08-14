package com.ipd.taxiu.ui.activity.address

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.widget.MessageDialog
import com.ipd.taxiu.widget.PickerUtil
import kotlinx.android.synthetic.main.activity_add_address.*

/**
Created by Miss on 2018/8/10
 *添加收货地址
 */
class AddAddressActivity : BaseUIActivity() {
    private val pickerUtil = PickerUtil()
    private val addressType by lazy { intent.getStringExtra("addressType") }

    companion object {
        fun launch(activity: Activity,addressType:String) {
            val intent = Intent(activity, AddAddressActivity::class.java)
            intent.putExtra("addressType",addressType)
            activity.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_add_address
    }

    override fun getToolbarTitle(): String = "添加收货地址"

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return if (addressType == "修改地址"){
            menuInflater.inflate(R.menu.menu_delete_address, menu)
            true
        }else false
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            initMessageDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun loadData() {
        pickerUtil.initJsonData(this)
    }

    override fun initListener() {
        btn_save.setOnClickListener {
            if (addressType == "添加地址") {
                toastShow("添加成功")
                finish()
            } else {
                toastShow("修改成功")
                finish()
            }
        }

        rl_address.setOnClickListener { pickerUtil.showPickerView(this, tv_choose_city) }
    }

    private fun initMessageDialog() {
        val builder = MessageDialog.Builder(this)
        builder.setTitle("确认要删除该收货地址吗？")
        builder.setMessage("删除后不可恢复，请谨慎操作")
        builder.setCommit("确认删除") { builder ->
            toastShow("删除成功")
            builder.dialog.dismiss()
            finish()
        }
        builder.setCancel("暂不删除") { builder -> builder.dialog.dismiss() }
        builder.dialog.show()
    }
}