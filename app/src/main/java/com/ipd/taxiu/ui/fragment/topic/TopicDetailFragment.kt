package com.ipd.taxiu.ui.fragment.topic

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.TopicDetailAdapter
import com.ipd.taxiu.bean.TopicCommentBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.topic.TopicPeopleCommentActivity
import rx.Observable

class TopicDetailFragment : ListFragment<List<TopicCommentBean>, TopicCommentBean>() {
    companion object {
        fun newInstance(): TopicDetailFragment {
            return TopicDetailFragment()
        }
    }

    override fun loadListData(): Observable<List<TopicCommentBean>> {
        return Observable.create<List<TopicCommentBean>> {
            val list = arrayListOf<TopicCommentBean>()
            for (i: Int in 0 until 30) {
                val info = TopicCommentBean()
                info.images = arrayListOf<Int>()
                for (j: Int in 0 until i % 4) {
                    info.images.add(R.mipmap.topic_detail_image)
                }
                list.add(info)
            }
            it.onNext(list)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: List<TopicCommentBean>): Int {
        return NORMAL
    }

    private var mAdapter: TopicDetailAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TopicDetailAdapter(mActivity, data, {
                //itemClick
                TopicPeopleCommentActivity.launch(mActivity)
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<TopicCommentBean>) {
        data?.addAll(result)
    }

}