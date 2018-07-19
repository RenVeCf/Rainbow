package com.ipd.taxiu.ui.activity.topic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.CommentReplyBean
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.widget.CommentsView
import kotlinx.android.synthetic.main.item_topic_people_comment_reply.*

class MoreCommentActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, MoreCommentActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "更多回复"

    override fun getContentLayout(): Int = R.layout.activity_more_recommend

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        val list: ArrayList<CommentReplyBean> = ArrayList()
        for (index: Int in 0 until 30) {
            val commentReplyBean = CommentReplyBean()
            commentReplyBean.content = "这个也是一种癖好"
            commentReplyBean.userName = "你觉得辣条好吃吗"
            commentReplyBean.replyName = if (index % 2 == 0) "你觉得辣条好吃吗" else ""
            list.add(commentReplyBean)
        }

        comments_view.visibility = View.VISIBLE
        comments_view.setList(list)
        comments_view.notifyDataSetChanged()
        comments_view.setOnItemClickListener(object : CommentsView.onItemClickListener {
            override fun onItemClick(position: Int, bean: CommentReplyBean?) {


            }

        })

    }

    override fun initListener() {

    }

}