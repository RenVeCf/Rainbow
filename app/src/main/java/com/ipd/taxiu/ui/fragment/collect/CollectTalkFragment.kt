package com.ipd.taxiu.ui.fragment.classroom

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.TalkAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.TalkBean
import com.ipd.taxiu.event.UpdateCollectTalkEvent
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.talk.TalkDetailActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class CollectTalkFragment : ListFragment<BaseResult<List<TalkBean>>, TalkBean>() {
    companion object {
        fun newInstance(): CollectTalkFragment {
            return CollectTalkFragment()
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

    override fun needLazyLoad() = true

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.empty_base)
    }

    override fun loadListData(): Observable<BaseResult<List<TalkBean>>> {
        return ApiManager.getService().talkCollect(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page)
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
        data?.addAll(result?.data ?: arrayListOf())
    }

    @Subscribe
    fun onMainEvent(event: UpdateCollectTalkEvent) {
        if (isFirstLoad()) return
        onRefresh(true)
    }

}