package com.ipd.taxiu.ui.fragment.topic

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.adapter.TopicPeopleCommentAdapter
import com.ipd.taxiu.bean.CommentReplyBean
import com.ipd.taxiu.bean.TopicCommentReplyBean
import com.ipd.taxiu.ui.ListFragment
import rx.Observable
import java.util.*

class TopicPeopleCommentFragment : ListFragment<List<TopicCommentReplyBean>, TopicCommentReplyBean>() {
    companion object {
        fun newInstance(): TopicPeopleCommentFragment {
            return TopicPeopleCommentFragment()
        }
    }

    override fun loadListData(): Observable<List<TopicCommentReplyBean>> {
        return Observable.create<List<TopicCommentReplyBean>> {
            val list = arrayListOf<TopicCommentReplyBean>()
            for (i: Int in 0 until 10) {
                val info = TopicCommentReplyBean()
                info.subCommentList = arrayListOf()

                val nextInt = Random().nextInt(10)
                for (j: Int in 0 until nextInt) {
                    val commentReplyBean = CommentReplyBean()
                    commentReplyBean.content = "这个也是一种癖好$j"
                    commentReplyBean.userName = "你觉得辣条好吃吗"
                    commentReplyBean.replyName = if (j % 2 == 0) "你觉得辣条好吃吗" else ""
                    info.subCommentList.add(commentReplyBean)
                }
                list.add(info)
            }
            it.onNext(list)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: List<TopicCommentReplyBean>): Int {
        return NORMAL
    }

    private var mAdapter: TopicPeopleCommentAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TopicPeopleCommentAdapter(mActivity, data, {
                //itemClick

            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<TopicCommentReplyBean>) {
        data?.addAll(result)
    }

}