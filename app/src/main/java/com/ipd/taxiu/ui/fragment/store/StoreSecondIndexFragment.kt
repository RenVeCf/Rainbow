package com.ipd.taxiu.ui.fragment.store

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.StoreAdapter
import com.ipd.taxiu.bean.StoreIndexVideoBean
import com.ipd.taxiu.bean.StoreRecommendProductHeaderBean
import com.ipd.taxiu.bean.StoreSecondIndexBean
import com.ipd.taxiu.bean.StoreSecondIndexHeaderBean
import com.ipd.taxiu.ui.ListFragment
import kotlinx.android.synthetic.main.fragment_store.view.*
import rx.Observable

class StoreSecondIndexFragment : ListFragment<StoreSecondIndexBean, Any>() {

    companion object {
        fun newInstance(type: Int): StoreSecondIndexFragment {
            val fragment = StoreSecondIndexFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val mType: Int by lazy { arguments.getInt("type") }
    override fun getContentLayout(): Int = R.layout.fragment_store

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        mContentView.iv_scroll_top.visibility = View.GONE
    }

    override fun initListener() {
        super.initListener()
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView == null || recyclerView.childCount <= 0) return
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                mContentView.iv_scroll_top.visibility = if (linearLayoutManager.findFirstVisibleItemPosition() > 0) View.VISIBLE else View.GONE
            }
        })

        mContentView.iv_scroll_top.setOnClickListener {
            recycler_view.smoothScrollToPosition(0)
        }
    }

    override fun loadListData(): Observable<StoreSecondIndexBean> {
        return Observable.create<StoreSecondIndexBean> {
            val storeInfo = StoreSecondIndexBean()
            storeInfo.headerInfo = StoreSecondIndexHeaderBean()
            storeInfo.recommendVideo = StoreIndexVideoBean()
            storeInfo.buildSpecialList()
            storeInfo.buildProductList()
            it.onNext(storeInfo)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: StoreSecondIndexBean): Int {
        return NORMAL
    }

    private var mAdapter: StoreAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = StoreAdapter(mActivity, data, { type ->
                isCreate = true
                onRefresh()
            })
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: StoreSecondIndexBean) {
        if (isRefresh) {
            data?.add(result.headerInfo)
            data?.addAll(result.specialList)
            data?.add(result.recommendVideo)
            data?.add(StoreRecommendProductHeaderBean())
        }
        data?.addAll(result.productList)
    }

    fun scrollTop() {
        recycler_view.smoothScrollToPosition(0)
    }


}