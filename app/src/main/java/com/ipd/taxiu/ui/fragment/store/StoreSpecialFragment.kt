package com.ipd.taxiu.ui.fragment.store

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils
import com.ipd.taxiu.MainActivity
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.StoreSpecialAdapter
import com.ipd.taxiu.bean.StoreIndexVideoBean
import com.ipd.taxiu.bean.StoreProductScreenBean
import com.ipd.taxiu.bean.StoreSpecialBean
import com.ipd.taxiu.bean.StoreSpecialHeaderBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.widget.ScreenLayout
import com.ipd.taxiu.widget.StoreSpecialRecyclerView
import kotlinx.android.synthetic.main.fragment_store_special.view.*
import rx.Observable

class StoreSpecialFragment : ListFragment<StoreSpecialBean, Any>() {

    companion object {
        fun newInstance(): StoreSpecialFragment {
            val fragment = StoreSpecialFragment()
            return fragment
        }
    }

    override fun getContentLayout(): Int = R.layout.fragment_store_special


    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        val screenLayout = mRootView?.findViewById<ScreenLayout>(R.id.screen_layout_container)
        screenLayout?.setBackgroupView(recycler_view)
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
            recycler_view?.smoothScrollToPosition(0)
        }
        mContentView.iv_store_home.setOnClickListener {
            MainActivity.launch(mActivity)
        }

        mContentView.swipe_target.setSuspensionListener(object : StoreSpecialRecyclerView.SuspensionListener {
            override fun onChange(isShow: Boolean) {
                LogUtils.e("tag", isShow.toString())
                val visibility = if (isShow) View.VISIBLE else View.GONE
                if (mContentView.screen_layout_container.visibility == visibility) return
                mContentView.screen_layout_container.visibility = visibility
            }

        })
    }

    override fun loadListData(): Observable<StoreSpecialBean> {
        return Observable.create<StoreSpecialBean> {
            val storeInfo = StoreSpecialBean()
            storeInfo.headerInfo = StoreSpecialHeaderBean()
//            storeInfo.recommendVideo = StoreIndexVideoBean()
            storeInfo.buildProductList()
            it.onNext(storeInfo)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: StoreSpecialBean): Int {
        return NORMAL
    }

    private var mAdapter: StoreSpecialAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = StoreSpecialAdapter(mActivity, data, {
                if (recycler_view.layoutManager is LinearLayoutManager) {
                    (recycler_view.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(it, -DensityUtil.dip2px(mActivity, 12f))
                }
            })
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: StoreSpecialBean) {
        if (isRefresh) {
            data?.add(result.headerInfo)
            data?.add(result.recommendVideo)
            data?.add(StoreProductScreenBean())
        }
        data?.addAll(result.productList)
    }


}