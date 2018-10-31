package com.ipd.taxiu.ui.fragment.talk

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.TalkDetailAdapter
import com.ipd.taxiu.bean.CommentResult
import com.ipd.taxiu.bean.MoreCommentReplyBean
import com.ipd.taxiu.bean.TalkCommentBean
import com.ipd.taxiu.bean.TalkDetailBean
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.presenter.TalkPostDetailChildPresenter
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.topic.MoreCommentActivity
import com.ipd.taxiu.utils.CommentType
import com.ipd.taxiu.widget.CommentSortLayout
import com.ipd.taxiu.widget.MessageDialog
import com.ipd.taxiu.widget.ReplyDialog
import kotlinx.android.synthetic.main.item_talk_comment.view.*
import kotlinx.android.synthetic.main.layout_post_user.view.*
import kotlinx.android.synthetic.main.layout_talk_header.view.*
import rx.Observable

class TalkDetailFragment : ListFragment<CommentResult<List<TalkCommentBean>>, TalkCommentBean>(), TalkPostDetailChildPresenter.ITalkPostDetailChildView {

    companion object {
        fun newInstance(talkId: Int, isMine: Boolean): TalkDetailFragment {
            val fragment = TalkDetailFragment()
            val bundle = Bundle()
            bundle.putBoolean("isMine", isMine)
            bundle.putInt("talkId", talkId)
            fragment.arguments = bundle
            return fragment
        }
    }


    private var mPresenter: TalkPostDetailChildPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = TalkPostDetailChildPresenter()
        mPresenter?.setType(CommentType.TALK)
        mPresenter?.attachView(mActivity, this)

    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }


    private val talkId: Int by lazy { arguments.getInt("talkId", -1) }
    private val isMine: Boolean by lazy { arguments.getBoolean("isMine") }
    private lateinit var detailData: TalkDetailBean

    fun setDetailData(data: TalkDetailBean) {
        detailData = data
    }

    private var mSortType = CommentSortLayout.TIME
    override fun loadListData(): Observable<CommentResult<List<TalkCommentBean>>> {
        return ApiManager.getService().talkComment(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, mSortType, talkId)
    }

    override fun isNoMoreData(result: CommentResult<List<TalkCommentBean>>): Int {
        if (page > INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: TalkDetailAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TalkDetailAdapter(mActivity, isMine, detailData, {
                mSortType = it
                onRefresh(true)
            }, data, { pos, res, info, replyInfo ->
                //itemClick
                when (res) {
                    R.id.tv_choose_best_answer -> {
                        if (info == null) return@TalkDetailAdapter
                        MessageDialog.Builder(context)
                                .setTitle("确认选择该答案为最佳答案吗？")
                                .setMessage("选择后不可撤销，请谨慎操作。")
                                .setCommit("确认选择", {
                                    it.dismiss()
                                    mPresenter?.bestAnswer(info.QUESTION_ID, info.ANSWER_ID)

                                })
                                .setCancel("暂不选择", {
                                    it.dismiss()
                                }).show()
                    }
                    R.id.comments_view -> {
                        //二级回复
                        if (info == null) return@TalkDetailAdapter
                        if (replyInfo == null) {
                            //更多回复
                            MoreCommentActivity.launch(mActivity, CommentType.TALK, info.ANSWER_ID)
                        } else {
                            ReplyDialog("回复:${replyInfo?.userName}", {
                                //二级回复
                                mPresenter?.secondReply(info.ANSWER_ID, replyInfo.USER_ID, it)
                            }).show(childFragmentManager, MoreCommentActivity::class.java.name)
                        }

                    }
                    R.id.iv_zan -> {
                        //内容点赞
                        mPresenter?.praise(pos, CommentType.TALK_PRAISE, detailData.QUESTION_ID)


                    }
                    R.id.ll_answer_zan -> {
                        //答案点赞
                        if (info == null) return@TalkDetailAdapter
                        mPresenter?.praise(pos, CommentType.TALK_PRAISE_COMMENT, info.ANSWER_ID)

                    }
                    R.id.ll_attention -> {
                        //答案点赞
                        mPresenter?.attention(detailData.User.USER_ID)
                    }
                    -1 -> {
                        //一级回复
                        if (info == null) return@TalkDetailAdapter
                        ReplyDialog("回复:${info.NICKNAME}", {
                            //一级回复
                            mPresenter?.secondReply(info.ANSWER_ID, info.USER_ID, it)
                        }).show(childFragmentManager, MoreCommentActivity::class.java.name)

                    }
                }

            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: CommentResult<List<TalkCommentBean>>) {
        detailData.COMMENT_NUM = result.join_num
        data?.addAll(result.data ?: arrayListOf())
    }

    override fun attentionSuccess(isAttent: Int) {
        detailData.User.IS_ATTEN = isAttent
        mAdapter?.setAttent(0, recycler_view.layoutManager.findViewByPosition(0).ll_attention)
    }

    override fun attentionFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun replySuccess() {
        onRefresh(true)
    }

    override fun replyFail(errMsg: String) {
        toastShow(errMsg)
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
                        holder.itemView.iv_answer_zan.isSelected = info.IS_PRAISE == 1
                        var num = holder.itemView.tv_answer_zan_num.text.toString().toInt()
                        holder.itemView.tv_answer_zan_num.text = if (info.IS_PRAISE == 1) "${num + 1}" else "${num - 1}"
                    }
                }
            }
        }
    }

    override fun praiseFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun bestAnswerSuccess() {
        detailData.IS_SURE = 1
        onRefresh(true)
    }

    override fun bestAnswerFail(errMsg: String) {
        toastShow(errMsg)
    }

}