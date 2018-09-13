package com.ipd.taxiu.ui.fragment.topic

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.TaxiuAdapter
import com.ipd.taxiu.adapter.TopicAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.TaxiuBean
import com.ipd.taxiu.bean.TopicBean
import com.ipd.taxiu.bean.TopicListBean
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.taxiu.TaxiuDetailActivity
import com.ipd.taxiu.ui.activity.topic.TopicDetailActivity
import rx.Observable

class TopicListFragment : ListFragment<BaseResult<List<TopicBean>>, TopicBean>() {
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
        progress_layout.setEmptyViewRes(R.layout.layout_empty_taxiu)
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
        data?.addAll(result.data)
    }

}