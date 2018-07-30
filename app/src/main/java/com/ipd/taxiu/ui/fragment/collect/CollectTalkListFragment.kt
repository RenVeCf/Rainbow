package com.ipd.taxiu.ui.fragment.collect

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.TalkAdapter
import com.ipd.taxiu.bean.TalkBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.talk.TalkDetailActivity
import rx.Observable

class CollectTalkListFragment : ListFragment<List<TalkBean>, TalkBean>() {
    companion object {
        fun newInstance(): CollectTalkListFragment {
            val topicListFragment = CollectTalkListFragment()
            return topicListFragment
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_talk)
    }

    override fun loadListData(): Observable<List<TalkBean>> {
        return Observable.create<List<TalkBean>> {
            val list: ArrayList<TalkBean> = ArrayList()
            for (i: Int in 0 until 10) {
                list.add(TalkBean())
            }
            it.onNext(list)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: List<TalkBean>): Int {
        if (result == null || result.isEmpty()) return EMPTY_DATA
        return NORMAL
    }

    private var mAdapter: TalkAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TalkAdapter(mActivity, data, {
                //itemClick
                TalkDetailActivity.launch(mActivity)
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<TalkBean>) {
        data?.addAll(result)
    }

}