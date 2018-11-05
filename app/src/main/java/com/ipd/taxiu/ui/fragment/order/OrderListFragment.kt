package com.ipd.taxiu.ui.fragment.order


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.MainActivity
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.OrderListAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.OrderBean
import com.ipd.taxiu.event.UpdateCartEvent
import com.ipd.taxiu.event.UpdateOrderEvent
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.presenter.order.OrderPresenter
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.order.EvaluateActivity
import com.ipd.taxiu.ui.activity.order.OrderDetailActivity
import com.ipd.taxiu.ui.activity.web.WebActivity
import com.ipd.taxiu.utils.Order
import com.ipd.taxiu.widget.MessageDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

/**
 * Created by Miss on 2018/7/19
 */
class OrderListFragment : ListFragment<BaseResult<List<OrderBean>>, OrderBean>(), Order.OrderItemClickListener, OrderPresenter.IOrderOperationView {
    companion object {
        fun newInstance(categoryId: Int): OrderListFragment {
            val fragment = OrderListFragment()
            val bundle = Bundle()
            bundle.putInt("categoryId", categoryId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun needLazyLoad() = true


    private var mPresenter: OrderPresenter<OrderPresenter.IOrderOperationView>? = null
    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
        mPresenter = OrderPresenter()
        mPresenter?.attachView(mActivity, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
        mPresenter?.detachView()
        mPresenter = null
    }


    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_order)
    }

    private val mCategoryId: Int by lazy { arguments?.getInt("categoryId") ?: 0 }
    override fun loadListData(): Observable<BaseResult<List<OrderBean>>> {
        return ApiManager.getService().orderList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, mCategoryId)
    }

    override fun isNoMoreData(result: BaseResult<List<OrderBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: OrderListAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = OrderListAdapter(context, data, this)
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<OrderBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

    override fun onItemClick(info: OrderBean) {
        OrderDetailActivity.launch(mActivity, info.ORDER_ID)
    }

    override fun onCancelOrder(info: OrderBean) {
        val builder = MessageDialog.Builder(mActivity)
        builder.setTitle("确认要取消该订单吗？")
                .setMessage("订单取消后不可恢复，需重新购买，请谨慎操作。")
                .setCommit("确认取消", {
                    it.dismiss()
                    mPresenter?.cancelOrder(info.ORDER_ID)
                })
                .setCancel("暂不取消", {
                    it.dismiss()
                }).show()
    }

    override fun onPayment(info: OrderBean) {
        onItemClick(info)
    }

    override fun onExpress(info: OrderBean) {
        WebActivity.launch(mActivity, WebActivity.URL, info.POST_INFO, "物流动态")
    }

    override fun onReceived(info: OrderBean) {
        val builder = MessageDialog.Builder(mActivity)
        builder.setTitle("确认要确认收货吗？")
                .setMessage("请确保您已收到商品，确认收货后，不可撤销，请谨慎操作。")
                .setCommit("确认收货", {
                    it.dismiss()
                    mPresenter?.receivedOrder(info.ORDER_ID)
                })
                .setCancel("暂不收货", {
                    it.dismiss()
                }).show()
    }

    override fun onBuyAgain(info: OrderBean) {
        mPresenter?.buyAgain(info.ORDER_ID)
    }

    override fun onEvaluate(info: OrderBean) {
        EvaluateActivity.launch(mActivity, info.ORDER_ID)
    }

    override fun cancelOrderSuccess() {
        onRefresh(true)
    }

    override fun cancelOrderFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun receivedOrderSuccess() {
        onRefresh(true)
    }

    override fun receivedOrderFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun deleteOrderSuccess() {
    }

    override fun deleteOrderFail(errMsg: String) {
    }

    override fun buyAgainSuccess() {
        val builder = MessageDialog.Builder(mActivity)
        builder.setTitle("提示")
                .setMessage("商品已加入购物车")
                .setCommit("前往购物车", {
                    it.dismiss()
                    EventBus.getDefault().post(UpdateCartEvent())
                    MainActivity.launch(mActivity, "cart")
                })
                .setCancel("取消", {
                    it.dismiss()
                }).show()
    }

    override fun buyAgainFail(errMsg: String) {
        toastShow(errMsg)
    }


    @Subscribe
    fun onMainEvent(event: UpdateOrderEvent) {
        if (isFirstLoad()) return
        if (mCategoryId == 0) {
            onRefresh(true)
        } else if (event.refreshPos.contains(mCategoryId)) {
            onRefresh(true)
        }
    }
}
