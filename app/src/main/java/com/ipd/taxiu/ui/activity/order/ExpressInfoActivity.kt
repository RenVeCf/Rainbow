package com.ipd.taxiu.ui.activity.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ReturnResult
import com.ipd.taxiu.event.UpdateReturnDetailEvent
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.widget.AuditSuccessDialog
import kotlinx.android.synthetic.main.activity_express_info.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Miss on 2018/7/23
 * 填写快递信息
 */
class ExpressInfoActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity, returnId: Int, expressNo: String?, expressCompany: String?) {
            val intent = Intent(activity, ExpressInfoActivity::class.java)
            intent.putExtra("returnId", returnId)
            intent.putExtra("expressNo", expressNo)
            intent.putExtra("expressCompany", expressCompany)
            activity.startActivity(intent)
        }
    }


    private val mReturnId: Int by lazy { intent.getIntExtra("returnId", -1) }
    private val mExpressNo: String by lazy { intent.getStringExtra("expressNo") ?: "" }
    private val mExpressCompany: String by lazy { intent.getStringExtra("expressCompany") ?: "" }
    override fun getToolbarTitle() = "填写快递信息"

    override fun getContentLayout(): Int {
        return R.layout.activity_express_info
    }

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        ApiManager.getService().returnExpressInfo(GlobalParam.getUserIdOrJump())
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<ReturnResult>() {
                    override fun _onNext(result: ReturnResult) {
                        if (result.code == 0) {
                            showContent()
                            loadReturnExpressSuccess(result)
                        } else {
                            showError(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        showError()
                    }
                })

    }

    private fun loadReturnExpressSuccess(result: ReturnResult) {
        tv_return_money_explanation.text = result.info
        tv_address.text = result.data.POST_ADDRESS
        tv_recipients.text = result.data.POST_NAME
        tv_contact_number.text = result.data.CUSTOMER_TEL
        tv_postage.text = result.data.POST_FEE

        et_express_no.setText(mExpressNo)
        et_express_company.setText(mExpressCompany)


        btn_commit.setOnClickListener {
            val expressNo = et_express_no.text.toString().trim()
            val expressCompany = et_express_company.text.toString().trim()
            if (TextUtils.isEmpty(expressNo)) {
                toastShow("请输入快递单号")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(expressCompany)) {
                toastShow("请输入快递公司")
                return@setOnClickListener
            }
            ApiManager.getService().returnCommitExpress(GlobalParam.getUserIdOrJump(), mReturnId, expressNo, expressCompany)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<ReturnResult>(mActivity, true) {
                        override fun _onNext(result: ReturnResult) {
                            if (result.code == 0) {
                                EventBus.getDefault().post(UpdateReturnDetailEvent())
                                initDialog()
                            } else {
                                toastShow(result.msg)
                            }
                        }
                    })

        }


    }

    override fun initListener() {
    }


    private fun initDialog() {
        val builder = AuditSuccessDialog.Builder(this)
        builder.setTitle("您的快递信息已提交")
        builder.setMessage("等待我司收货并确认")
        builder.setCommit("我知道了") { builder ->
            builder.dialog.dismiss()
            finish()
        }
        builder.dialog.show()
    }

}
