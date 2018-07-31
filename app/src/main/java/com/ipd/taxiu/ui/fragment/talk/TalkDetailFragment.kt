package com.ipd.taxiu.ui.fragment.talk

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.TalkDetailAdapter
import com.ipd.taxiu.bean.CommentReplyBean
import com.ipd.taxiu.bean.TalkCommentBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.widget.MessageDialog
import kotlinx.android.synthetic.main.item_talk_comment.view.*
import rx.Observable
import java.util.*
import kotlin.collections.ArrayList

class TalkDetailFragment : ListFragment<List<TalkCommentBean>, TalkCommentBean>() {
    companion object {
        fun newInstance(isMine: Boolean): TalkDetailFragment {
            val fragment = TalkDetailFragment()
            val bundle = Bundle()
            bundle.putBoolean("isMine", isMine)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val isMine: Boolean by lazy { arguments.getBoolean("isMine") }
    override fun loadListData(): Observable<List<TalkCommentBean>> {
        return Observable.create<List<TalkCommentBean>> {
            val list = arrayListOf<TalkCommentBean>()
            for (i: Int in 0 until 30) {
                val info = TalkCommentBean()
                info.replyList = ArrayList()
                val nextInt = Random().nextInt(10)
                for (j: Int in 0 until nextInt) {
                    val commentReplyBean = CommentReplyBean()
                    commentReplyBean.content = "建议楼主立即就医$j"
                    commentReplyBean.userName = "你觉得辣条好吃吗"
                    commentReplyBean.replyName = if (j % 2 == 0) "你觉得辣条好吃吗" else ""
                    info.replyList.add(commentReplyBean)
                }
                list.add(info)
            }
            it.onNext(list)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: List<TalkCommentBean>): Int {
        return NORMAL
    }

    private var mAdapter: TalkDetailAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TalkDetailAdapter(mActivity, isMine, data, { res, info ->
                //itemClick
                when (res) {
                    R.id.tv_choose_best_answer -> {
                        MessageDialog.Builder(context)
                                .setTitle("确认选择该答案为最佳答案吗？")
                                .setMessage("选择后不可撤销，请谨慎操作。")
                                .setCommit("确认选择", {
                                    it.dismiss()
                                })
                                .setCancel("暂不选择", {
                                    it.dismiss()
                                }).show()
                    }
                    -1 -> {

                    }
                }

            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<TalkCommentBean>) {
        data?.addAll(result)
    }

}