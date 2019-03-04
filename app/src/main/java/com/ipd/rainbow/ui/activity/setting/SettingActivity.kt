package com.ipd.rainbow.ui.activity.setting

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.rainbow.R
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.HttpUrl
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.activity.address.DeliveryAddressActivity
import com.ipd.rainbow.ui.activity.address.OrderPeopleActivity
import com.ipd.rainbow.ui.activity.web.WebActivity
import com.ipd.rainbow.utils.CleanMessageUtil
import com.ipd.rainbow.widget.MessageDialog
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseUIActivity() {

    override fun getToolbarTitle(): String {
        return "设置"
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_setting
    }

    override fun initView(bundle: Bundle?) {
        initToolbar()
        try {
            tv_cache.text = CleanMessageUtil.getTotalCacheSize(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun loadData() {

    }

    override fun initListener() {
        ll_update_password.setOnClickListener {
            //修改密码
            val intent = Intent(this, UpdatePasswordActivity::class.java)
            startActivity(intent)
        }
        ll_order_people.setOnClickListener {
            //订购人
            OrderPeopleActivity.launch(mActivity, OrderPeopleActivity.NORMAL)
        }
        ll_address.setOnClickListener {
            //收货地址
            DeliveryAddressActivity.launch(mActivity, DeliveryAddressActivity.NORMAL)
        }
        ll_about_us.setOnClickListener {
            //关于我们
            WebActivity.launch(mActivity, WebActivity.URL, HttpUrl.WEB_URL + HttpUrl.ABOUT_US, "关于我们")
        }
        ll_service_phone.setOnClickListener {
            //服务电话
            initDialog("确认要拨打客服电话吗？", "彩虹购官方客服电话： " + tv_service_phone.text.toString().trim(), "确认拨打", "暂不拨打")
        }
        ll_common_problem.setOnClickListener {
            //常见问题
            val intent = Intent(this, CommonProblemActivity::class.java)
            startActivity(intent)
        }
        ll_clear_cache.setOnClickListener {
            //清除缓存
            clearCacheDialog()
        }
        exit_account.setOnClickListener {
            //退出账号
            initExitDialog()
        }
    }


    private fun initDialog(title: String, message: String, commitStr: String, cancelStr: String) {
        val builder = MessageDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCommit(commitStr) { builder ->
            CommonUtils.callPhone(this@SettingActivity, tv_service_phone.text.toString().trim())
            builder.dialog.dismiss()
        }
        builder.setCancel(cancelStr) { builder -> builder.dialog.dismiss() }
        builder.dialog.show()
    }

    private fun clearCacheDialog() {
        val builder = MessageDialog.Builder(this)
        builder.setTitle("提示")
        builder.setMessage("你确定要清除缓存吗？")
        builder.setCommit("确定") { builder ->
            CleanMessageUtil.clearAllCache(applicationContext)
            try {
                tv_cache!!.text = CleanMessageUtil.getTotalCacheSize(applicationContext)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            builder.dialog.dismiss()
        }
        builder.setCancel("取消") { builder -> builder.dialog.dismiss() }
        builder.dialog.show()
    }

    private fun initExitDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("确定要退出该登录账户吗？")
        builder.setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
        builder.setPositiveButton("确定") { dialog, which ->
            dialog.dismiss()
            GlobalParam.onExitUser()
            GlobalParam.isLoginOrJump()
        }
        builder.show()
    }
}
