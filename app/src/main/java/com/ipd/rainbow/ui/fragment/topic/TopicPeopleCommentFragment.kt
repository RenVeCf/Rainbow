package com.ipd.rainbow.ui.fragment.topic

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.TopicPeopleCommentAdapter
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.CommentDetailBean
import com.ipd.rainbow.bean.MoreCommentReplyBean
import com.ipd.rainbow.bean.TopicCommentReplyBean
import com.ipd.rainbow.event.UpdateTaxiuDetailCommentEvent
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.presenter.store.PeopleCommentPresenter
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.ui.activity.topic.MoreCommentActivity
import com.ipd.rainbow.utils.ReplyType
import com.ipd.rainbow.utils.comment.CommentApiFactory
import com.ipd.rainbow.utils.comment.ICommentApi
import com.ipd.rainbow.widget.ReplyDialog
import kotlinx.android.synthetic.main.item_topic_people_comment_reply.view.*
import kotlinx.android.synthetic.main.layout_topic_people_comment_header.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class TopicPeopleCommentFragment : ListFragment<BaseResult<List<TopicCommentReplyBean>>, TopicCommentReplyBean>(), PeopleCommentPresenter.IPeopleCommentView {
    companion object {
        fun newInstance(type: Int, commentId: Int): TopicPeopleCommentFragment {
            val fragment = TopicPeopleCommentFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            bundle.putInt("commentId", commentId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var mPresenter: PeopleCommentPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
        mPresenter = PeopleCommentPresenter()
        mPresenter?.attachView(mActivity, this)

    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
        mPresenter?.detachView()
        mPresenter = null
    }

    private val type: Int by lazy { arguments.getInt("type") }
    private val commentId: Int by lazy { arguments.getInt("commentId") }
    private val commentApi: ICommentApi by lazy { CommentApiFactory.createCommentApi(type) }


    private lateinit var mDetailData: CommentDetailBean
    fun setData(data: CommentDetailBean) {
        mDetailData = data
    }

    override fun loadListData(): Observable<BaseResult<List<TopicCommentReplyBean>>> {
        return commentApi.replyList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, commentId)
    }

    override fun isNoMoreData(result: BaseResult<List<TopicCommentReplyBean>>): Int {
        if (page > INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: TopicPeopleCommentAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TopicPeopleCommentAdapter(mActivity, mDetailData, data) { pos, replyType, replyId, info, replyInfo ->
                //itemClick
                when (replyType) {
                    ReplyType.MORE_REPLY -> {
                        MoreCommentActivity.launch(context as Activity, type, replyId)
                    }
                    ReplyType.SECOND_REPLY -> {
                        if (info == null || replyInfo == null) return@TopicPeopleCommentAdapter
                        ReplyDialog("回复:${replyInfo?.userName}") {
                            //二级回复
                            commentApi.secondReply(GlobalParam.getUserIdOrJump(), replyId, replyInfo?.USER_ID, it)
                                    .compose(RxScheduler.applyScheduler())
                                    .subscribe(object : Response<BaseResult<MoreCommentReplyBean>>(mActivity, true) {
                                        override fun _onNext(result: BaseResult<MoreCommentReplyBean>) {
                                            if (result.code == 0) {
                                                onRefresh()
                                            } else {
                                                toastShow(result.msg)
                                            }
                                        }
                                    })


                        }.show(childFragmentManager, MoreCommentActivity::class.java.name)
                    }
                    ReplyType.PRAISE_COMMENT -> {
                        //对评论点赞
                        mPresenter?.praise(pos, commentApi.getPraiseCommentCategory(), commentId)
                    }
                    ReplyType.PRAISE_REPLY -> {
                        //对回复点赞
                        mPresenter?.praise(pos, commentApi.getPraiseReplyCategory(), replyId)
                    }
                    ReplyType.ATTENTION -> {
                        //关注
                        mPresenter?.attention(replyId)
                    }
                }


            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<TopicCommentReplyBean>>) {
        data?.addAll(result.data ?: arrayListOf())
    }

    fun refreshUI() {
        onRefresh()
    }

    @Subscribe
    fun onMainEvent(event: UpdateTaxiuDetailCommentEvent) {
        refreshUI()
    }

    override fun attentionSuccess(isAttent: Int) {
        mDetailData.User.IS_ATTEN = isAttent
        mAdapter?.setAttent(0, recycler_view.layoutManager.findViewByPosition(0).ll_attent)

    }

    override fun attentionFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun praiseSuccess(pos: Int, category: String) {
        when (category) {
            commentApi.getPraiseCommentCategory() -> {
                //刷新头部
                mDetailData.IS_PRAISE = if (mDetailData.IS_PRAISE == 1) 0 else 1
                val holder = recycler_view.findViewHolderForAdapterPosition(pos)
                if (holder != null) {
                    holder.itemView.iv_comment_zan.isSelected = mDetailData.IS_PRAISE == 1
                    var num = holder.itemView.tv_comment_zan_num.text.toString().toInt()
                    holder.itemView.tv_comment_zan_num.text = if (mDetailData.IS_PRAISE == 1) "${num + 1}" else "${num - 1}"
                    holder.itemView.tv_comment_zan_num.setTextColor(if (mDetailData.IS_PRAISE == 1)mActivity.resources.getColor(R.color.colorPrimaryDark) else mActivity.resources.getColor(R.color.LightGrey))

                }
            }
            commentApi.getPraiseReplyCategory() -> {
                //刷新回复
                val info = mAdapter?.getItemInfo(pos)
                if (info != null) {
                    info.IS_PRAISE = if (info.IS_PRAISE == 1) 0 else 1
                    val holder = recycler_view.findViewHolderForAdapterPosition(pos)
                    if (holder != null) {
                        holder.itemView.iv_sub_comment_zan.isSelected = info.IS_PRAISE == 1
                        var num = holder.itemView.tv_sub_comment_zan_num.text.toString().toInt()
                        holder.itemView.tv_sub_comment_zan_num.text = if (info.IS_PRAISE == 1) "${num + 1}" else "${num - 1}"
                        holder.itemView.tv_sub_comment_zan_num.setTextColor(if (info.IS_PRAISE == 1) mActivity.resources.getColor(R.color.colorPrimaryDark) else mActivity.resources.getColor(R.color.LightGrey))

                    }
                }

            }
        }
    }

    override fun praiseFail(errMsg: String) {
        toastShow(errMsg)
    }

}