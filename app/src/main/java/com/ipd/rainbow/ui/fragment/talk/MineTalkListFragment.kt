package com.ipd.rainbow.ui.fragment.talk

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.TalkAdapter
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.TalkBean
import com.ipd.rainbow.event.UpdateTalkListEvent
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.ui.activity.talk.TalkDetailActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class MineTalkListFragment : ListFragment<BaseResult<List<TalkBean>>, TalkBean>() {
    companion object {
        fun newInstance(categoryId: Int): MineTalkListFragment {
            val topicListFragment = MineTalkListFragment()
            val bundle = Bundle()
            bundle.putInt("categoryId", categoryId)
            topicListFragment.arguments = bundle
            return topicListFragment
        }
    }

    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
    }

    override fun needLazyLoad(): Boolean = true

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_talk)
    }

    private val categoryId: Int by lazy { arguments.getInt("categoryId", 0) }
    override fun loadListData(): Observable<BaseResult<List<TalkBean>>> {
        return ApiManager.getService().mineTalkList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, categoryId)
    }

    override fun isNoMoreData(result: BaseResult<List<TalkBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: TalkAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TalkAdapter(mActivity, data, {
                //itemClick
                TalkDetailActivity.launch(mActivity, it.QUESTION_ID, it.User.NICKNAME, it.USER_ID.toString() == GlobalParam.getUserId())
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<TalkBean>>) {
        data?.addAll(result?.data?: arrayListOf())
    }

    @Subscribe
    fun onMainEvent(event: UpdateTalkListEvent) {
        onRefresh()
    }

}