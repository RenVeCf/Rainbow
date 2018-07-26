package com.ipd.taxiu.ui.fragment.store.grouppurchase

import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil
import com.ipd.taxiu.adapter.PurchaseProductAdapter
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.bean.PurchaseProductBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import com.ipd.taxiu.utils.StringUtils
import kotlinx.android.synthetic.main.item_group_purchase.view.*
import rx.Observable
import java.util.*


class GroupPurchaseFragment : ListFragment<List<PurchaseProductBean>, PurchaseProductBean>() {

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

    override fun loadListData(): Observable<List<PurchaseProductBean>> {
        return Observable.create<List<PurchaseProductBean>> {
            val purchaseProductList = ArrayList<PurchaseProductBean>()
            for (index: Int in 0 until 10) {
                val info = PurchaseProductBean()
                info.startTime = System.currentTimeMillis()
                info.endTime = System.currentTimeMillis() + 1000 * 60 * 60
                info.productInfo = ProductBean()
                purchaseProductList.add(info)
            }
            it.onNext(purchaseProductList)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: List<PurchaseProductBean>): Int {
        return NORMAL
    }

    private var mAdapter: PurchaseProductAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            recycler_view.addItemDecoration(object :RecyclerView.ItemDecoration(){
                override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                    outRect?.bottom = DensityUtil.dip2px(mActivity,8f)
                }
            })
            mAdapter = PurchaseProductAdapter(mActivity, data, {
                //商品详情
                ProductDetailActivity.launch(mActivity)
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<PurchaseProductBean>) {
        data?.addAll(result)
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
                        val surplusTime = data!![i].endTime - System.currentTimeMillis()
                        StringUtils.getCountDownByTime(surplusTime,{hours, minutes, second ->
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