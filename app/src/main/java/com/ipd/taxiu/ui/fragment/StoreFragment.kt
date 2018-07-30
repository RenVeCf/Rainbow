package com.ipd.taxiu.ui.fragment

import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.StoreAdapter
import com.ipd.taxiu.bean.StoreIndexBean
import com.ipd.taxiu.bean.StoreIndexHeaderBean
import com.ipd.taxiu.bean.StoreIndexVideoBean
import com.ipd.taxiu.bean.StoreRecommendProductHeaderBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.store.ProductCategoryActivity
import com.ipd.taxiu.ui.activity.store.StoreSearchActivity
import kotlinx.android.synthetic.main.fragment_store.view.*
import kotlinx.android.synthetic.main.store_toolbar.view.*
import rx.Observable

class StoreFragment : ListFragment<StoreIndexBean, Any>() {
    override fun getTitleLayout(): Int = R.layout.store_toolbar

    override fun getContentLayout(): Int = R.layout.fragment_store

    override fun initListener() {
        super.initListener()
        mContentView.iv_scroll_top.setOnClickListener {
            recycler_view.smoothScrollToPosition(0)
        }
        mHeaderView.tv_search.setOnClickListener {
            StoreSearchActivity.launch(mActivity)
        }
        mHeaderView.iv_category.setOnClickListener {
            ProductCategoryActivity.launch(mActivity)
        }
    }

    private var mType = StoreIndexBean.DOG
    override fun loadListData(): Observable<StoreIndexBean> {
        return Observable.create<StoreIndexBean> {
            val storeInfo = StoreIndexBean(mType)
            storeInfo.headerInfo = StoreIndexHeaderBean(mType)
            storeInfo.recommendVideo = StoreIndexVideoBean()
            storeInfo.buildSpecialList()
            storeInfo.buildProductList()
            it.onNext(storeInfo)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: StoreIndexBean): Int {
        return NORMAL
    }

    private var mAdapter: StoreAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = StoreAdapter(mActivity, data, { type ->
                mType = type
                isCreate = true
                onRefresh()
            })
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: StoreIndexBean) {
        if (isRefresh) {
            data?.add(result.headerInfo)
            data?.addAll(result.specialList)
            data?.add(result.recommendVideo)
            data?.add(StoreRecommendProductHeaderBean())
        }
        data?.addAll(result.productList)
    }


}