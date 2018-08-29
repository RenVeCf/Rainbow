package com.ipd.taxiu.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ClassRoomAdapter
import com.ipd.taxiu.adapter.TalkAdapter
import com.ipd.taxiu.adapter.TaxiuAdapter
import com.ipd.taxiu.adapter.TopicAdapter
import com.ipd.taxiu.bean.ClassRoomBean
import com.ipd.taxiu.bean.TalkBean
import com.ipd.taxiu.bean.TaxiuBean
import com.ipd.taxiu.bean.TopicBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.SearchActivity
import com.ipd.taxiu.ui.activity.classroom.ClassRoomDetailActivity
import com.ipd.taxiu.ui.activity.talk.TalkDetailActivity
import com.ipd.taxiu.ui.activity.taxiu.TaxiuDetailActivity
import com.ipd.taxiu.ui.activity.topic.TopicDetailActivity
import rx.Observable
import java.util.concurrent.TimeUnit

class SearchFragment : ListFragment<List<Any>, Any>() {
    companion object {
        fun newInstance(searchType: SearchActivity.SearchType): SearchFragment {
            val topicListFragment = SearchFragment()
            val bundle = Bundle()
            bundle.putSerializable("searchType", searchType)
            topicListFragment.arguments = bundle
            return topicListFragment
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_topic)
    }

    private val mSearchType: SearchActivity.SearchType by lazy { arguments.getSerializable("searchType") as SearchActivity.SearchType }

    override fun loadDataWhenVisible() {
    }

    override fun loadListData(): Observable<List<Any>> {
        return Observable.create<List<Any>> { sub ->
            Observable.timer(2000L, TimeUnit.MILLISECONDS).subscribe {
                var list: ArrayList<Any> = arrayListOf()
                when (mSearchType) {
                    SearchActivity.SearchType.TOPIC -> {
                        for (index: Int in 0 until 10) {
                            list.add(TopicBean())
                        }
                    }
                    SearchActivity.SearchType.CLASSROOM -> {
                        for (index: Int in 0 until 10) {
                            list.add(ClassRoomBean())
                        }
                    }
                    SearchActivity.SearchType.TAXIU -> {
                        for (index: Int in 0 until 10) {
                            list.add(TaxiuBean())
                        }
                    }
                    SearchActivity.SearchType.TALK -> {
                        for (index: Int in 0 until 10) {
                            list.add(TalkBean())
                        }
                    }
                }
                sub.onNext(list)
                sub.onCompleted()
            }
        }
    }

    override fun isNoMoreData(result: List<Any>): Int {
        if (result == null || result.isEmpty()) return EMPTY_DATA
        return NORMAL
    }

    private var mAdapter: RecyclerView.Adapter<*>? = null
    private fun getAdapter(): RecyclerView.Adapter<*> {
        return when (mSearchType) {
            SearchActivity.SearchType.TALK -> {
                TalkAdapter(mActivity, data as List<TalkBean>, {
                    //itemClick
                    TalkDetailActivity.launch(mActivity)
                })
            }
            SearchActivity.SearchType.CLASSROOM -> {
                ClassRoomAdapter(mActivity, data as List<ClassRoomBean>, {
                    //itemClick
                    ClassRoomDetailActivity.launch(mActivity)
                })
            }
            SearchActivity.SearchType.TAXIU -> {
                TaxiuAdapter(mActivity, data as List<TaxiuBean>, {
                    //itemClick
                    TaxiuDetailActivity.launch(mActivity)
                })
            }
            else -> {
                TopicAdapter(mActivity, data as List<TopicBean>, {
                    //itemClick
//                    TopicDetailActivity.launch(mActivity)
                })
            }
        }
    }

    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = getAdapter()
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<Any>) {
        data?.addAll(result)
    }

    fun search(searchStr: String) {
        isCreate = true
        onRefresh()
    }

}