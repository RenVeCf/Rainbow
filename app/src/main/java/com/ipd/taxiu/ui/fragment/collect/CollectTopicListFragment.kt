package com.ipd.taxiu.ui.fragment.collect

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.TopicAdapter
import com.ipd.taxiu.bean.TopicBean
import com.ipd.taxiu.bean.TopicListBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.topic.TopicDetailActivity
import rx.Observable

class CollectTopicListFragment : ListFragment<TopicListBean, TopicBean>() {
    companion object {
        fun newInstance(): CollectTopicListFragment {
            val topicListFragment = CollectTopicListFragment()
            return topicListFragment
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_topic)
    }

    override fun loadListData(): Observable<TopicListBean> {
        return Observable.create<TopicListBean> {
            val topicListBean = TopicListBean()
            topicListBean.list = ArrayList()
            for (i: Int in 0 until 10) {
                topicListBean.list.add(TopicBean())
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
                TopicDetailActivity.launch(mActivity)
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