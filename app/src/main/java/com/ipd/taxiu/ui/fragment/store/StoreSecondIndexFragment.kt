package com.ipd.taxiu.ui.fragment.store

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.StoreAdapter
import com.ipd.taxiu.bean.*
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.utils.StorePetSpecialType
import com.ipd.taxiu.utils.StoreType
import kotlinx.android.synthetic.main.fragment_store.view.*
import rx.Observable

class StoreSecondIndexFragment : ListFragment<BaseResult<List<ProductBean>>, Any>() {

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

    override fun needLazyLoad(): Boolean = true

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        mContentView.iv_scroll_top.visibility = View.GONE
    }


    private lateinit var mStoreIndexInfo: StoreSecondIndexBean

    override fun loadDataWhenVisible() {
        LogUtils.e("TAG", "loadDataWhenVisible:$mType")
        super.loadDataWhenVisible()
    }

    override fun getListData(isRefresh: Boolean) {
        if (isRefresh) {
            checkNeedShowProgress()
            ApiManager.getService().storeSecondIndex(GlobalParam.getUserIdOrJump(), StorePetSpecialType.getParentTypeByType(mType), mType)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<StoreSecondIndexResultBean>>() {
                        override fun _onNext(result: BaseResult<StoreSecondIndexResultBean>) {
                            if (result.code == 0) {
                                val data = result.data
                                mStoreIndexInfo = StoreSecondIndexBean()
                                mStoreIndexInfo.headerInfo = StoreSecondIndexHeaderBean()
                                //banner
                                mStoreIndexInfo.headerInfo.bannerList = data.BANNER_LIST
                                //分类
                                mStoreIndexInfo.headerInfo.categoryList = data.TYPE_LIST
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
    }

    override fun loadListData(): Observable<BaseResult<List<ProductBean>>> {
        return ApiManager.getService().storeGuessLike(mType, StoreType.getGuessLikeTypeBySpecial(mType), Constant.PAGE_SIZE, GlobalParam.getUserIdOrJump(), page)

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
        data?.addAll(result?.data ?: arrayListOf())
    }

    fun scrollTop() {
        recycler_view.smoothScrollToPosition(0)
    }


}