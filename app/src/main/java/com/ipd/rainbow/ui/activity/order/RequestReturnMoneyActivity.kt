package com.ipd.rainbow.ui.activity.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ReturnOrderInfoBean
import com.ipd.rainbow.bean.ReturnReasonBean
import com.ipd.rainbow.event.UpdateOrderDetailEvent
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.presenter.store.RequestReturnPresenter
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.utils.Order
import com.ipd.rainbow.widget.AuditSuccessDialog
import com.ipd.rainbow.widget.PickerUtil
import kotlinx.android.synthetic.main.activity_request_return_money.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Miss on 2018/7/23
 * 申请退款
 */
class RequestReturnMoneyActivity : BaseUIActivity(), RequestReturnPresenter.IRequestReturnView {

    companion object {
        fun launch(activity: Activity, orderId: Int, orderDetailId: Int) {
            val intent = Intent(activity, RequestReturnMoneyActivity::class.java)
            intent.putExtra("orderId", orderId)
            intent.putExtra("orderDetailId", orderDetailId)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String {
        return "申请退款"
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_request_return_money
    }

    private var mOrderStatus: Int = -1
    private val mOrderId: Int by lazy { intent.getIntExtra("orderId", 0) }
    private val mOrderDetailId: Int by lazy { intent.getIntExtra("orderDetailId", 0) }
    private var mPresenter: RequestReturnPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = RequestReturnPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        mPresenter?.loadReturnOrderInfo(mOrderId, mOrderDetailId)
    }

    override fun initListener() {
        btn_submit.setOnClickListener {
            val num = cart_operation_view.getNum()
            isAccpeted({ msg, isAccepted ->
                if (!TextUtils.isEmpty(msg)) {
                    toastShow(msg!!)
                    return@isAccpeted
                }
                val returnReason = tv_return_reason.text.toString().trim()
                if (TextUtils.isEmpty(returnReason)) {
                    toastShow("请选择退款原因")
                    return@isAccpeted
                }

                val question = et_question.text.toString().trim()
                val pictureList = picture_recycler_view.getPictureList()
                var picStr = ""
                var uploadStatus = true
                pictureList.forEach {
                    if (TextUtils.isEmpty(it.url)) {
                        uploadStatus = false
                        return@forEach
                    }
                    picStr += "${it.url};"
                }
                if (!uploadStatus) {
                    toastShow("正在上传图片...")
                    return@isAccpeted
                }

                mPresenter?.commitReturnReason(mOrderId, mOrderDetailId, num, isAccepted.toString(), returnReason, question, picStr)
            })

        }
    }

    private fun isAccpeted(callback: (msg: String?, isAccepted: Int) -> Unit) {
        var isAccepted: Boolean
        when (mOrderStatus) {
            Order.WAIT_RECEIVE -> {
                val acceptedProduct = tv_accepted_product.text.toString().trim()
                if (TextUtils.isEmpty(acceptedProduct)) {
                    callback.invoke("请选择是否收到商品", 0)
                    return
                }
                isAccepted = acceptedProduct == acceptedList[1]
            }
            Order.WAIT_SEND -> {
                isAccepted = false
            }
            else -> {
                isAccepted = true
            }
        }
        callback.invoke(null, if (isAccepted) 2 else 1)
    }

    override fun loadReturnOrderInfoSuccess(info: ReturnOrderInfoBean) {
        mOrderStatus = info.ORDER_STATUS

        ImageLoader.loadNoPlaceHolderImg(mActivity, info.ORDER_DETAIL.LOGO, iv_product_img)
        tv_product_name.text = info.ORDER_DETAIL.NAME
        tv_product_explane.text = info.ORDER_DETAIL.NORM
        tv_product_price.text = "￥${info.ORDER_DETAIL.CURRENT_PRICE}"
        tv_buyed_num.text = "x${info.ORDER_DETAIL.BUY_NUM}"
        cart_operation_view.setMaxNum(info.ORDER_DETAIL.BUY_NUM)
        tv_contact_phone.text = info.ABOUT_US.PHONE
        tv_work_time.text = info.ABOUT_US.WORK_TIME
        tv_call_phone.setOnClickListener {
            CommonUtils.callPhone(mActivity, info.ABOUT_US.PHONE)
        }

        if (mOrderStatus == Order.WAIT_RECEIVE) {
            ll_accepted_product.visibility = View.VISIBLE
        } else {
            ll_accepted_product.visibility = View.GONE
        }

        ll_accepted_product.setOnClickListener {
            PickerUtil().initRetrunMoneyAccept(mActivity, acceptedList, tv_accepted_product)
        }

        ll_return_reason.setOnClickListener {
            showReturnReason()
        }

        //问题描述
        if (mOrderStatus == Order.EVALUATE || mOrderStatus == Order.FINFISH) {
            ll_question.visibility = View.VISIBLE
            picture_recycler_view.init(4)
        }


        showContent()

    }

    val acceptedList = arrayListOf("未收到商品", "已收到商品")
    private fun showReturnReason() {
//        var isAccepted = false
//        if (mOrderStatus == Order.WAIT_RECEIVE) {
//            val acceptedProduct = tv_accepted_product.text.toString().trim()
//            if (TextUtils.isEmpty(acceptedProduct)) {
//                toastShow("请选择是否收到商品")
//                return
//            }
//            isAccepted = acceptedProduct == acceptedList[1]
//        }
        mPresenter?.loadReturnReason()
    }

    override fun loadReturnOrderInfoFail(errMsg: String) {
        showError(errMsg)
    }

    override fun loadReturnReasonSuccess(list: List<ReturnReasonBean>) {
        PickerUtil().initRetrunMoneyOption(mActivity, list, tv_return_reason)
    }

    override fun loadReturnReasonFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun requestReturnSuccess() {
        initDialog()
    }

    override fun requestReturnFail(errMsg: String) {
        toastShow(errMsg)
    }

    private fun initDialog() {
        val builder = AuditSuccessDialog.Builder(this)
        builder.setTitle("您的退款申请已提交")
        builder.setMessage("正在等待管理员审核")
        builder.setCommit("我知道了") { builder ->
            builder.dialog.dismiss()
            EventBus.getDefault().post(UpdateOrderDetailEvent())
            finish()
        }
        builder.dialog.show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        picture_recycler_view.onActivityResult(requestCode, resultCode, data)
    }

}
