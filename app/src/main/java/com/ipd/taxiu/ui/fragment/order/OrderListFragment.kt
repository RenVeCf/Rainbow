package com.ipd.taxiu.ui.fragment.order


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.OrderListAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.OrderBean
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.order.OrderDetailActivity
import com.ipd.taxiu.utils.Order
import rx.Observable

/**
 * Created by Miss on 2018/7/19
 */
class OrderListFragment : ListFragment<BaseResult<List<OrderBean>>, OrderBean>(), Order.OrderItemClickListener {

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
    private var mAdapter: OrderListAdapter? = null

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
    }

    override fun onPayment(info: OrderBean) {
    }

    override fun onExpress(info: OrderBean) {
    }

    override fun onReceived(info: OrderBean) {
    }

    override fun onBuyAgain(info: OrderBean) {
    }

    override fun onEvaluate(info: OrderBean) {
    }
}
