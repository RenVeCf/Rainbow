package com.ipd.taxiu.ui.fragment.topic

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.TopicAdapter
import com.ipd.taxiu.bean.TopicBean
import com.ipd.taxiu.bean.TopicListBean
import com.ipd.taxiu.ui.ListFragment
import rx.Observable

class TopicListFragment : ListFragment<TopicListBean, TopicBean>() {
    companion object {
        fun newInstance(categoryId: Int): TopicListFragment {
            val topicListFragment = TopicListFragment()
            val bundle = Bundle()
            bundle.putInt("categoryId", categoryId)
            topicListFragment.arguments = bundle
            return topicListFragment
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_topic)
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

    private var mAdapter: TopicAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TopicAdapter(mActivity, data, {
                //itemClick

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