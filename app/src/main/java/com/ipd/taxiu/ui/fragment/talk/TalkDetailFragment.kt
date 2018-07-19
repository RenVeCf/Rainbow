package com.ipd.taxiu.ui.fragment.talk

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.adapter.TalkDetailAdapter
import com.ipd.taxiu.bean.CommentReplyBean
import com.ipd.taxiu.bean.TalkCommentBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.topic.TopicPeopleCommentActivity
import rx.Observable
import java.util.*
import kotlin.collections.ArrayList

class TalkDetailFragment : ListFragment<List<TalkCommentBean>, TalkCommentBean>() {
    companion object {
        fun newInstance(): TalkDetailFragment {
            return TalkDetailFragment()
        }
    }

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
            mAdapter = TalkDetailAdapter(mActivity, data, {
                //itemClick
                
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