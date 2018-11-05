package com.ipd.taxiu.ui.fragment.store.grouppurchase

import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil
import com.ipd.taxiu.adapter.PurchaseProductAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.PurchaseProductBean
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import com.ipd.taxiu.utils.StringUtils
import kotlinx.android.synthetic.main.item_group_purchase.view.*
import rx.Observable
import java.util.*


class GroupPurchaseFragment : ListFragment<BaseResult<List<PurchaseProductBean>>, PurchaseProductBean>() {

    companion object {
        fun newInstance(): GroupPurchaseFragment {
            val fragment = GroupPurchaseFragment()
            return fragment
        }
    }

    private var mProductTimerTask: ProductTimerTask? = null
    private val mTimer: Timer = Timer()
    override fun onViewAttach() {
        super.onViewAttach()
        mProductTimerTask?.cancel()
        mProductTimerTask = ProductTimerTask()
        mTimer.schedule(mProductTimerTask, Date(System.currentTimeMillis()), 1000L)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mProductTimerTask?.cancel()
        mProductTimerTask = null
        mTimer?.cancel()
    }

    override fun loadListData(): Observable<BaseResult<List<PurchaseProductBean>>> {
        return ApiManager.getService().storeSpell(GlobalParam.getUserId(), Constant.PAGE_SIZE, page)
    }

    override fun isNoMoreData(result: BaseResult<List<PurchaseProductBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: PurchaseProductAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            recycler_view.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                    outRect?.bottom = DensityUtil.dip2px(mActivity, 8f)
                }
            })
            mAdapter = PurchaseProductAdapter(mActivity, data, {
                //商品详情
                ProductDetailActivity.launch(mActivity, it.PRODUCT_ID, it.FORM_ID)
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<PurchaseProductBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }


    internal inner class ProductTimerTask : TimerTask() {
        override fun run() {
            if (recycler_view.layoutManager == null) return
            val layoutManager = recycler_view.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            mActivity.runOnUiThread({
                for (i in firstVisibleItemPosition..lastVisibleItemPosition) {
                    val childView = layoutManager.findViewByPosition(i)
                    if (childView != null) {
                        var surplusTime = data!![i].endTime - System.currentTimeMillis()
                        if (surplusTime < 0) surplusTime = 0

                        StringUtils.getCountDownByTime(surplusTime, { hours, minutes, second ->
                            childView.tv_group_purchase_hours.text = hours
                            childView.tv_group_purchase_minute.text = minutes
                            childView.tv_group_purchase_second.text = second
                        })

                    }

                }
            })


        }
    }


}