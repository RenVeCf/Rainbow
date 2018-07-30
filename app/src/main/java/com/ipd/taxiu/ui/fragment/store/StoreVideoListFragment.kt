package com.ipd.taxiu.ui.fragment.store

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.StoreVideoAdapter
import com.ipd.taxiu.bean.TopicBean
import com.ipd.taxiu.bean.TopicListBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.store.video.StoreVideoDetailActivity
import rx.Observable

class StoreVideoListFragment : ListFragment<TopicListBean, TopicBean>() {
    companion object {
        fun newInstance(categoryId: Int): StoreVideoListFragment {
            val fragment = StoreVideoListFragment()
            val bundle = Bundle()
            bundle.putInt("categoryId", categoryId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_video)
        swipe_load_layout.isRefreshEnabled = false
    }

    private val categoryId: Int by lazy { arguments.getInt("categoryId", 0) }
    override fun loadListData(): Observable<TopicListBean> {
        return Observable.create<TopicListBean> {
            val topicListBean = TopicListBean()
            topicListBean.list = ArrayList()
            if (categoryId != 2) {
                for (i: Int in 0 until 10) {
                    topicListBean.list.add(TopicBean())
                }
            }
            it.onNext(topicListBean)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: TopicListBean): Int {
        if (result.list == null || result.list.isEmpty()) return EMPTY_DATA
        return NORMAL
    }

    private var mAdapter: StoreVideoAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = StoreVideoAdapter(mActivity, data, {
                //itemClick
                StoreVideoDetailActivity.launch(mActivity)

            })
            recycler_view.addItemDecoration(object :RecyclerView.ItemDecoration(){
                override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                    outRect?.bottom = 2
                }
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: TopicListBean) {
        data?.addAll(result.list)
    }

}