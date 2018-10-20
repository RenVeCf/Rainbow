package com.ipd.taxiu.ui.activity.order

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import butterknife.ButterKnife
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.widget.AuditSuccessDialog
import com.ipd.taxiu.widget.PickerUtil
import java.util.*

/**
 * Created by Miss on 2018/7/23
 * 申请退款
 */
class RequestReturnMoneyActivity : BaseUIActivity(), View.OnClickListener {

    companion object {
        fun launch(activity: Activity, orderId: Int, orderDetailId: Int) {
            val intent = Intent(activity, RequestReturnMoneyActivity::class.java)
            intent.putExtra("orderId", orderId)
            intent.putExtra("orderDetailId", orderDetailId)
            activity.startActivity(intent)
        }
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_request_return_money
    }

    override fun initView(bundle: Bundle?) {
        initToolbar()
//        tv_return_explanation!!.text = "1、请先收到商品后，再进行申请退款操作，请勿拆开快递包裹；\n" +
//                "2、您的退款申请提交后，待我们收到您寄回的商品后，我司会进行审核工作；\n" +
//                "3、审核通过后，请将您收到的商品，按照快递单上的的地址寄回即可，寄回的快递费用由您承担，如是到付，我司一律不签收，一切后果自付；\n" +
//                "4、我司收到您寄回的商品并确认无问题后，我们会在1~2个工作日内将订单实付金额退还到您的余额。"
    }

    override fun loadData() {
    }

    override fun initListener() {
    }

    override fun getToolbarTitle(): String {
        return "申请退款"
    }

    private fun initDialog() {
        val builder = AuditSuccessDialog.Builder(this)
        builder.setTitle("您的退款申请已提交")
        builder.setMessage("正在等待管理员审核")
        builder.setCommit("我知道了") { builder ->
            builder.dialog.dismiss()
            finish()
        }
        builder.dialog.show()

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.btn_submit -> initDialog()
//            R.id.tv_return_reason -> {
//                val pickerUtil = PickerUtil()
//                pickerUtil.initRetrunMoneyOption(this, returnReason, tv_return_reason)
//            }
//            R.id.tv_call_phone -> CommonUtils.callPhone(this, tv_contact_phone!!.text.toString())
//        }

    }
}
