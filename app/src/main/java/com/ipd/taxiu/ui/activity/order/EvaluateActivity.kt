package com.ipd.taxiu.ui.activity.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.WindowManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.EvaluateAdapter
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.event.UpdateOrderDetailEvent
import com.ipd.taxiu.event.UpdateOrderEvent
import com.ipd.taxiu.presenter.order.OrderEvaluatePresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_evaluate.*
import kotlinx.android.synthetic.main.footer_evaluvate.view.*
import kotlinx.android.synthetic.main.item_evaluate.view.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Miss on 2018/7/21
 * 评价商品
 */
class EvaluateActivity : BaseUIActivity(), OrderEvaluatePresenter.IOrderEvaluateView {

    companion object {
        fun launch(activity: Activity, orderId: Int) {
            val intent = Intent(activity, EvaluateActivity::class.java)
            intent.putExtra("orderId", orderId)
            activity.startActivity(intent)
        }
    }

    private val mOrderId by lazy { intent.getIntExtra("orderId", -1) }

    private var mAdapter: EvaluateAdapter? = null

    override fun getContentLayout(): Int {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        return R.layout.activity_evaluate
    }

    private var mPresenter: OrderEvaluatePresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = OrderEvaluatePresenter()
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
        mPresenter?.loadEvaluateProductList(mOrderId)
    }

    override fun initListener() {
    }

    override fun getToolbarTitle(): String {
        return "发表评价"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            if (mAdapter == null || mAdapter!!.getSelectPosition() < 0) return
            val itemView = (recycler_view_evaluate.layoutManager as LinearLayoutManager).findViewByPosition(mAdapter!!.getSelectPosition())
            itemView.picture_recycler_view.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun loadEvaluateProductListSuccess(list: List<ProductBean>) {
        mAdapter = EvaluateAdapter(this, list)
        recycler_view_evaluate.layoutManager = LinearLayoutManager(this)
        recycler_view_evaluate.addItemDecoration(SpaceItemDecoration(24))
        recycler_view_evaluate.adapter = mAdapter
        val footerView = LayoutInflater.from(this).inflate(R.layout.footer_evaluvate, null)
        mAdapter?.addFooterView(footerView)

        btn_evaluate.setOnClickListener {
            mAdapter?.getProductEvaluateInfo({ errMsg, productEvaluateList ->
                if (TextUtils.isEmpty(errMsg)) {
                    if (productEvaluateList == null || productEvaluateList.isEmpty()) {
                        toastShow("未找到待评价商品")
                        return@getProductEvaluateInfo
                    }
                    val descStar = footerView.rating_star1.star
                    val expressStar = footerView.rating_star2.star
                    val serviceStar = footerView.rating_star3.star

                    when {
                        descStar == 0f -> {
                            toastShow("请对描述相符打分")
                            return@getProductEvaluateInfo
                        }
                        expressStar == 0f -> {
                            toastShow("请对物流服务打分")
                            return@getProductEvaluateInfo
                        }
                        serviceStar == 0f -> {
                            toastShow("请对服务态度打分")
                            return@getProductEvaluateInfo
                        }
                    }
                    mPresenter?.publishEvaluate(mOrderId, productEvaluateList, descStar.toInt(), serviceStar.toInt(), expressStar.toInt())

                } else {
                    toastShow(errMsg)
                }
            })

        }

        showContent()
    }

    override fun loadEvaluateProductListFail(errMsg: String) {
        showError(errMsg)
    }

    override fun publishEvaluateSuccess() {
        EventBus.getDefault().post(UpdateOrderEvent(intArrayOf(0, 4)))
        EventBus.getDefault().post(UpdateOrderDetailEvent())
        toastShow(true, "评价成功")
        finish()

    }

    override fun publishEvaluateFail(errMsg: String) {
        toastShow(errMsg)
    }
}
