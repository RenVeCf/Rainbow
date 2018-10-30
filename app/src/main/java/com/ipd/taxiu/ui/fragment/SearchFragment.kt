package com.ipd.taxiu.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ClassRoomAdapter
import com.ipd.taxiu.adapter.TalkAdapter
import com.ipd.taxiu.adapter.TaxiuAdapter
import com.ipd.taxiu.adapter.TopicAdapter
import com.ipd.taxiu.bean.*
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.SearchActivity
import com.ipd.taxiu.ui.activity.classroom.ClassRoomDetailActivity
import com.ipd.taxiu.ui.activity.talk.TalkDetailActivity
import com.ipd.taxiu.ui.activity.taxiu.TaxiuDetailActivity
import com.ipd.taxiu.ui.activity.topic.TopicDetailActivity
import rx.Observable


class SearchFragment : ListFragment<BaseResult<List<Any>>, Any>() {
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
        progress_layout.setEmptyViewRes(R.layout.layout_empty_search)
    }

    private val mSearchType: SearchActivity.SearchType by lazy { arguments.getSerializable("searchType") as SearchActivity.SearchType }

    override fun loadDataWhenVisible() {
    }

    private var searchStr: String = ""

    override fun loadListData(): Observable<BaseResult<List<Any>>> {
        val observable: Observable<BaseResult<List<Any>>> = when (mSearchType) {
            SearchActivity.SearchType.TAXIU -> {
                ApiManager.getService().taxiuList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, 0, searchStr)
                        .map {
                            it as BaseResult<List<Any>>
                        }
            }
            SearchActivity.SearchType.TOPIC -> {
                ApiManager.getService().topicList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, 0, searchStr)
                        .map {
                            it as BaseResult<List<Any>>
                        }
            }
            SearchActivity.SearchType.TALK -> {
                ApiManager.getService().talkList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, 0, searchStr, "0")
                        .map {
                            it as BaseResult<List<Any>>
                        }
            }
            else -> {
                ApiManager.getService().classroomList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, searchStr)
                        .map {
                            it as BaseResult<List<Any>>
                        }
            }
        }
        return observable
    }

    override fun isNoMoreData(result: BaseResult<List<Any>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: RecyclerView.Adapter<*>? = null
    private fun getAdapter(): RecyclerView.Adapter<*> {
        return when (mSearchType) {
            SearchActivity.SearchType.TALK -> {
                TalkAdapter(mActivity, data as List<TalkBean>, {
                    //itemClick
                    TalkDetailActivity.launch(mActivity, it.QUESTION_ID, it.User.NICKNAME, it.USER_ID.toString() == GlobalParam.getUserId())
                })
            }
            SearchActivity.SearchType.CLASSROOM -> {
                ClassRoomAdapter(mActivity, data as List<ClassRoomBean>, false,{
                    //itemClick
                    ClassRoomDetailActivity.launch(mActivity, it.CLASS_ROOM_ID)
                })
            }
            SearchActivity.SearchType.TAXIU -> {
                TaxiuAdapter(mActivity, data as List<TaxiuBean>, {
                    //itemClick
                    TaxiuDetailActivity.launch(mActivity, it.SHOW_ID, GlobalParam.getUserId() == it.USER_ID.toString())
                })
            }
            else -> {
                TopicAdapter(mActivity, data as List<TopicBean>, {
                    //itemClick
                    TopicDetailActivity.launch(mActivity, it.TOPIC_ID)
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

    override fun addData(isRefresh: Boolean, result: BaseResult<List<Any>>) {
        data?.addAll(result?.data?: arrayListOf())
    }

    fun search(searchStr: String) {
        this.searchStr = searchStr
        isCreate = true
        onRefresh()
    }

}