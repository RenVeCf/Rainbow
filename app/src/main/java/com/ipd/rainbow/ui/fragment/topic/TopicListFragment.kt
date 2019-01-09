package com.ipd.rainbow.ui.fragment.topic

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.TopicAdapter
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.TopicBean
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.ui.activity.topic.TopicDetailActivity
import rx.Observable

open class TopicListFragment : ListFragment<BaseResult<List<TopicBean>>, TopicBean>() {
    companion object {
        fun newInstance(categoryId: Int): TopicListFragment {
            val topicListFragment = TopicListFragment()
            val bundle = Bundle()
            bundle.putInt("categoryId", categoryId)
            topicListFragment.arguments = bundle
            return topicListFragment
        }
    }

    override fun needLazyLoad(): Boolean = true

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_topic)
    }

    private val categoryId: Int by lazy { arguments.getInt("categoryId", 0) }
    override fun loadListData(): Observable<BaseResult<List<TopicBean>>> {
        return ApiManager.getService().topicList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, categoryId, "")
    }

    override fun isNoMoreData(result: BaseResult<List<TopicBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: TopicAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TopicAdapter(mActivity, data, {
                //itemClick
                TopicDetailActivity.launch(mActivity, it.TOPIC_ID)
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<TopicBean>>) {
        data?.addAll(result?.data?: arrayListOf())
    }

}