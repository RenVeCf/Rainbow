package com.ipd.taxiu.ui.fragment.taxiu

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.TaxiuDetailAdapter
import com.ipd.taxiu.bean.TaxiuCommentBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.topic.TopicPeopleCommentActivity
import rx.Observable

class TaxiuDetailFragment : ListFragment<List<TaxiuCommentBean>, TaxiuCommentBean>() {
    companion object {
        fun newInstance(): TaxiuDetailFragment {
            return TaxiuDetailFragment()
        }
    }

    override fun loadListData(): Observable<List<TaxiuCommentBean>> {
        return Observable.create<List<TaxiuCommentBean>> {
            val list = arrayListOf<TaxiuCommentBean>()
            for (i: Int in 0 until 30) {
                val info = TaxiuCommentBean()
                info.images = arrayListOf()
                for (j: Int in 0 until i % 4) {
                    info.images.add(R.mipmap.topic_detail_image)
                }
                list.add(info)
            }
            it.onNext(list)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: List<TaxiuCommentBean>): Int {
        return NORMAL
    }

    private var mAdapter: TaxiuDetailAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TaxiuDetailAdapter(mActivity, data, {
                //itemClick
                TopicPeopleCommentActivity.launch(mActivity)
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<TaxiuCommentBean>) {
        data?.addAll(result)
    }

}