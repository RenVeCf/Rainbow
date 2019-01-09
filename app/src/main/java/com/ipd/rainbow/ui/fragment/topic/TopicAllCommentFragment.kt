package com.ipd.rainbow.ui.fragment.topic

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.TopicAllCommentAdapter
import com.ipd.rainbow.bean.CommentResult
import com.ipd.rainbow.bean.TopicCommentBean
import com.ipd.rainbow.event.UpdateTopicCommentEvent
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.presenter.store.PostDetailChildPresenter
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.ui.activity.topic.TopicPeopleCommentActivity
import com.ipd.rainbow.utils.CommentType
import com.ipd.rainbow.widget.CommentSortLayout
import kotlinx.android.synthetic.main.item_topic_comment.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class TopicAllCommentFragment : ListFragment<CommentResult<List<TopicCommentBean>>, TopicCommentBean>(), PostDetailChildPresenter.ITaxiuDetailChildView {
    companion object {
        fun newInstance(topicId: Int): TopicAllCommentFragment {
            val fragment = TopicAllCommentFragment()
            val bundle = Bundle()
            bundle.putInt("topicId", topicId)
            fragment.arguments = bundle
            return fragment
        }
    }


    private var mPresenter: PostDetailChildPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
        mPresenter = PostDetailChildPresenter()
        mPresenter?.setType(CommentType.TOPIC)
        mPresenter?.attachView(mActivity, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
        mPresenter?.detachView()
        mPresenter = null
    }


    private val topicId: Int by lazy { arguments.getInt("topicId", -1) }

    private var mSortType = CommentSortLayout.TIME
    override fun loadListData(): Observable<CommentResult<List<TopicCommentBean>>> {
        return ApiManager.getService().topicComment(GlobalParam.getUserId(), Constant.PAGE_SIZE, page, mSortType, topicId)
    }

    override fun isNoMoreData(result: CommentResult<List<TopicCommentBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: TopicAllCommentAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TopicAllCommentAdapter(mActivity, {
                mSortType = it
                onRefresh(true)
            }, data, { pos, res, info ->
                //itemClick
                when (res) {
                    R.id.ll_comment_zan -> {
                        mPresenter?.praise(pos, CommentType.TOPIC_PRAISE_COMMENT, info!!.PARTAKE_ID)
                    }
                    else -> {
                        TopicPeopleCommentActivity.launch(mActivity, info!!.NICKNAME, CommentType.TOPIC, info.PARTAKE_ID)
                    }
                }
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: CommentResult<List<TopicCommentBean>>) {
        data?.addAll(result.data ?: arrayListOf())
    }


    @Subscribe
    fun onMainEvent(event: UpdateTopicCommentEvent) {
        onRefresh()
    }


    override fun attentionSuccess(isAttent: Int) {
    }

    override fun attentionFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun praiseSuccess(pos: Int, category: String) {
        val info = data?.get(pos)
        if (info != null) {
            info.IS_PRAISE = if (info.IS_PRAISE == 1) 0 else 1
            val holder = recycler_view.findViewHolderForAdapterPosition(pos)
            if (holder != null) {
                holder.itemView.iv_comment_zan.isSelected = info.IS_PRAISE == 1
                var num = holder.itemView.tv_comment_zan_num.text.toString().toInt()
                holder.itemView.tv_comment_zan_num.text = if (info.IS_PRAISE == 1) "${num + 1}" else "${num - 1}"
                holder.itemView.tv_comment_zan_num.isSelected = info.IS_PRAISE == 1

            }
        }
    }

    override fun praiseFail(errMsg: String) {
    }

}