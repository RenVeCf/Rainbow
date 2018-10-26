package com.ipd.taxiu.ui.activity.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ReturnPictureAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ReturnDetailBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.activity.PictureLookActivity
import com.ipd.taxiu.utils.Order
import com.ipd.taxiu.utils.Return
import com.ipd.taxiu.utils.StringUtils
import kotlinx.android.synthetic.main.return_detail_info.*

/**
 * Created by Miss on 2018/7/23
 * 退货详情
 */
class ReturnDetailActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity, returnId: Int) {
            val intent = Intent(activity, ReturnDetailActivity::class.java)
            intent.putExtra("returnId", returnId)
            activity.startActivity(intent)
        }
    }


    private val mReturnId: Int by lazy { intent.getIntExtra("returnId", -1) }
    override fun getToolbarTitle(): String = "退款记录详情"


    override fun getContentLayout(): Int = R.layout.activity_return_detail

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        ApiManager.getService().returnDetail(GlobalParam.getUserIdOrJump(), mReturnId)
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<ReturnDetailBean>>() {
                    override fun _onNext(result: BaseResult<ReturnDetailBean>) {
                        if (result.code == 0) {
                            showContent()
                            loadReturnDetailSuccess(result.data)
                        } else {
                            showError(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        showError()
                    }
                })

    }

    private fun loadReturnDetailSuccess(info: ReturnDetailBean) {
        ImageLoader.loadNoPlaceHolderImg(mActivity, info.ORDER_DETAIL.LOGO, iv_product_img)
        tv_product_name.text = info.ORDER_DETAIL.PROCUCT_NAME
        tv_product_explane.text = info.ORDER_DETAIL.TASTE
        tv_product_price.text = "￥${info.ORDER_DETAIL.CURRENT_PRICE}"
        tv_buyed_num.text = "x${info.ORDER_DETAIL.BUY_NUM}"
        tv_apply_num.text = "x${info.APPLY_NUM}"
        tv_order_code.text = info.ORDER_NO
        tv_pay_money.text = "￥${info.ORDER_PAY_FEE}"
        tv_order_status.text = Order.getOrderStrByStatus(info.ORDER_STATUS)
        tv_is_receive.text = if (TextUtils.isEmpty(info.ORDER_RECEIPT_TIME)) "未收到商品" else "已收到商品"
        tv_order_time.text = info.CREATETIME
        tv_pay_time.text = info.ORDER_PAYTIME
        tv_delivery_time.text = info.ORDER_RECEIPT_TIME

        //退款原因
        tv_reason_return_content.text = info.REASON
        tv_reason_update_time.text = "提交时间：${info.CREATETIME}"

        //问题描述
        tv_question_info.text = info.CONTENT
        val pics = StringUtils.splitImages(info.PIC)
        if (pics.isNotEmpty()) {
            picture_recycler_view.visibility = View.VISIBLE
            picture_recycler_view.adapter = ReturnPictureAdapter(mActivity, pics, { list, pos ->
                PictureLookActivity.launch(mActivity, ArrayList(list), pos, PictureLookActivity.URL)
            })
        } else {
            picture_recycler_view.visibility = View.GONE
        }


        //审核状态
        if (info.STATUS == 1) {
            rl_auth.visibility = View.GONE
        } else {
            rl_auth.visibility = View.VISIBLE
            tv_auth_status.text = "(${Return.getAuthStrByStatus(info.STATUS)})"
            tv_auth_result.text = info.CHECK_INFO
            tv_auth_time.text = info.CHECKTIME
        }

        //填写快递信息
        if (info.STATUS != 1 && !TextUtils.isEmpty(info.REFUND_DETAIL.POST_NUM) && !TextUtils.isEmpty(info.REFUND_DETAIL.POST_COMPANY)) {
            btn_express.visibility = View.GONE
            rl_express.visibility = View.VISIBLE
            tv_express_no.text = "回寄快递单号：${info.REFUND_DETAIL.POST_NUM}"
            tv_express_company.text = "快递公司：${info.REFUND_DETAIL.POST_COMPANY}"
            tv_express_time.text = "提交时间：${info.REFUND_DETAIL.CREATETIME}"

        } else {
            rl_express.visibility = View.GONE
            btn_express.visibility = if (info.STATUS == 2) View.VISIBLE else View.GONE
        }
        btn_express.setOnClickListener {
            ExpressInfoActivity.launch(mActivity, info.REFUND_ID, info.REFUND_DETAIL.POST_NUM, info.REFUND_DETAIL.POST_COMPANY)
        }

    }

    override fun initListener() {

    }

}