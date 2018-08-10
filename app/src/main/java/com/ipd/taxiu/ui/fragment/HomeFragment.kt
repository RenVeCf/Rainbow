package com.ipd.taxiu.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.HomeAdapter
import com.ipd.taxiu.bean.HomeBean
import com.ipd.taxiu.ui.ListFragment
import kotlinx.android.synthetic.main.fragment_home.view.*
import rx.Observable

class HomeFragment : ListFragment<HomeBean, Any>() {

    override fun getContentLayout(): Int = R.layout.fragment_home

    override fun loadListData(): Observable<HomeBean> {
        return Observable.create {
            val homeBean = HomeBean()
            homeBean.buildInfo()
            it.onNext(homeBean)
            it.onCompleted()
        }
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

    override fun isNoMoreData(result: HomeBean): Int {
        return NORMAL
    }

    private var mAdapter: HomeAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = HomeAdapter(mActivity, data)
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: HomeBean) {
        if (isRefresh) {
            data?.add(result.banner)
            data?.add(result.boutique)
            data?.add(result.topic)
            data?.add(result.talk)
            data?.add(result.classRoom)
        }
        data?.addAll(result.taxiuList)
    }

}