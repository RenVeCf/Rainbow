package com.ipd.taxiu.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.StoreAdapter
import com.ipd.taxiu.bean.*
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.store.ProductCategoryActivity
import com.ipd.taxiu.ui.activity.store.StoreSearchActivity
import kotlinx.android.synthetic.main.fragment_store.view.*
import kotlinx.android.synthetic.main.store_toolbar.view.*
import rx.Observable

class StoreFragment : ListFragment<BaseResult<List<ProductBean>>, Any>() {
    override fun getTitleLayout(): Int = R.layout.store_toolbar

    override fun getContentLayout(): Int = R.layout.fragment_store

    private lateinit var mStoreIndexInfo: StoreIndexBean

    override fun getListData(isRefresh: Boolean) {
        if (isRefresh) {
            checkNeedShowProgress()
            ApiManager.getService().storeIndex(GlobalParam.getUserIdOrJump(), mType)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<StoreIndexResultBean>>() {
                        override fun _onNext(result: BaseResult<StoreIndexResultBean>) {
                            if (result.code == 0) {
                                val data = result.data
                                mStoreIndexInfo = StoreIndexBean(mType)
                                mStoreIndexInfo.headerInfo = StoreIndexHeaderBean(mType)
                                //banner
                                mStoreIndexInfo.headerInfo.bannerList = data.BANNER_LIST
                                //分类
                                mStoreIndexInfo.headerInfo.categoryList = data.TYPE_LIST
                                //区域分区
                                mStoreIndexInfo.headerInfo.areaList = data.AREA_LIST
                                //小banner
                                mStoreIndexInfo.headerInfo.smallBannerList = data.SEASON_BANNER_LIST
                                //专区
                                mStoreIndexInfo.specialList = data.PRODUCT_LIST.map { it.TYPE_DATA }
                                //推荐视频
                                mStoreIndexInfo.recommendVideo = StoreIndexVideoBean(data.VIDEO_LIST)

                                getParentListData(isRefresh)
                            } else {
                                showError(result.msg)
                            }

                        }

                        override fun onError(e: Throwable?) {
                            showError("连接服务器失败")
                        }

                    })
        } else {
            super.getListData(isRefresh)
        }
    }

    private fun getParentListData(isRefresh: Boolean) {
        super.getListData(isRefresh)
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
        mHeaderView.tv_search.setOnClickListener {
            StoreSearchActivity.launch(mActivity)
        }
        mHeaderView.iv_category.setOnClickListener {
            ProductCategoryActivity.launch(mActivity)
        }
    }

    private var mType = StoreIndexBean.DOG
    override fun loadListData(): Observable<BaseResult<List<ProductBean>>> {
        return ApiManager.getService().storeGuessLike(mType, Constant.PAGE_SIZE, GlobalParam.getUserIdOrJump(), page)
    }

    override fun isNoMoreData(result: BaseResult<List<ProductBean>>): Int {
        if (page > INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return NO_MORE_DATA
        }
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

    override fun addData(isRefresh: Boolean, result: BaseResult<List<ProductBean>>) {
        if (isRefresh) {
            data?.add(mStoreIndexInfo.headerInfo)
            data?.addAll(mStoreIndexInfo.specialList)
            data?.add(mStoreIndexInfo.recommendVideo)
            data?.add(StoreRecommendProductHeaderBean())
        }
        data?.addAll(result?.data?: arrayListOf())
    }


}