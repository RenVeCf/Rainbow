package com.ipd.taxiu.ui.fragment.topic

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.TopicDetailAdapter
import com.ipd.taxiu.bean.CommentResult
import com.ipd.taxiu.bean.MoreCommentReplyBean
import com.ipd.taxiu.bean.TopicCommentBean
import com.ipd.taxiu.bean.TopicDetailBean
import com.ipd.taxiu.event.UpdateTopicCommentEvent
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.presenter.store.PostDetailChildPresenter
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.topic.TopicPeopleCommentActivity
import com.ipd.taxiu.utils.CommentType
import kotlinx.android.synthetic.main.item_topic_comment.view.*
import kotlinx.android.synthetic.main.layout_topic_header.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class TopicDetailFragment : ListFragment<CommentResult<List<TopicCommentBean>>, TopicCommentBean>(), PostDetailChildPresenter.ITaxiuDetailChildView {
    companion object {
        fun newInstance(topicId: Int): TopicDetailFragment {
            val fragment = TopicDetailFragment()
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
    private lateinit var detailData: TopicDetailBean

    fun setDetailData(data: TopicDetailBean) {
        detailData = data
    }

    override fun loadListData(): Observable<CommentResult<List<TopicCommentBean>>> {
        return ApiManager.getService().topicComment(GlobalParam.getUserId(), Constant.PAGE_SIZE, page, 1, topicId)
    }

    override fun isNoMoreData(result: CommentResult<List<TopicCommentBean>>): Int {
        if (page > INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: TopicDetailAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TopicDetailAdapter(mActivity, detailData, data, { pos, res, info ->
                //itemClick
                when (res) {
                    R.id.iv_zan -> {
                        mPresenter?.praise(pos, CommentType.TOPIC_PRAISE, detailData.TOPIC_ID)
                    }
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
        detailData.COMMENT_NUM = result.join_num
        data?.addAll(result.data ?: arrayListOf())
    }


    @Subscribe
    fun onMainEvent(event: UpdateTopicCommentEvent) {
        onRefresh()
    }


    override fun attentionSuccess(detail: MoreCommentReplyBean) {

    }

    override fun attentionFail(errMsg: String) {
    }

    override fun praiseSuccess(pos: Int, category: String) {
        when (pos) {
            0 -> {
                detailData.IS_PRAISE = if (detailData.IS_PRAISE == 1) 0 else 1
                val holder = recycler_view.findViewHolderForAdapterPosition(pos)
                if (holder != null) {
                    holder.itemView.iv_zan.isSelected = detailData.IS_PRAISE == 1
                    var num = holder.itemView.tv_zan.text.toString().toInt()
                    holder.itemView.tv_zan.text = if (detailData.IS_PRAISE == 1) "${num + 1}" else "${num - 1}"
                }
            }
            else -> {
                val info = data?.get(pos - 1)
                if (info != null) {
                    info.IS_PRAISE = if (info.IS_PRAISE == 1) 0 else 1
                    val holder = recycler_view.findViewHolderForAdapterPosition(pos)
                    if (holder != null) {
                        holder.itemView.iv_comment_zan.isSelected = info.IS_PRAISE == 1
                        var num = holder.itemView.tv_comment_zan_num.text.toString().toInt()
                        holder.itemView.tv_comment_zan_num.text = if (info.IS_PRAISE == 1) "${num + 1}" else "${num - 1}"
                    }
                }
            }
        }
    }

    override fun praiseFail(errMsg: String) {
    }


}