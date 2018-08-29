package com.ipd.taxiu.ui.activity.topic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.CommentReplyBean
import com.ipd.taxiu.bean.MoreCommentReplyBean
import com.ipd.taxiu.event.UpdateTaxiuDetailCommentEvent
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.presenter.store.MoreCommentPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.widget.CommentsView
import com.ipd.taxiu.widget.ReplyDialog
import kotlinx.android.synthetic.main.activity_more_recommend.*
import kotlinx.android.synthetic.main.item_topic_people_comment_reply.*
import org.greenrobot.eventbus.EventBus

class MoreCommentActivity : BaseUIActivity(), MoreCommentPresenter.IMoreCommentView {

    companion object {
        fun launch(activity: Activity, type: Int, replyId: Int) {
            val intent = Intent(activity, MoreCommentActivity::class.java)
            intent.putExtra("replyId", replyId)
            intent.putExtra("type", type)
            activity.startActivity(intent)
        }
    }

    private val replyId by lazy { intent.getIntExtra("replyId", -1) }
    private val type by lazy { intent.getIntExtra("type", -1) }

    override fun getToolbarTitle(): String = "更多回复"

    override fun getContentLayout(): Int = R.layout.activity_more_recommend

    private var mPresenter: MoreCommentPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = MoreCommentPresenter()
        mPresenter?.setType(type)
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        loadMoreComment(true)
    }

    private fun loadMoreComment(needProgress: Boolean) {
        if (needProgress) showProgress()
        mPresenter?.loadMoreComment(needProgress, replyId)
    }

    override fun initListener() {


    }

    override fun loadDetailSuccess(needProgress: Boolean, info: MoreCommentReplyBean) {
        if (needProgress) showContent()
        ImageLoader.loadAvatar(mActivity, info.LOGO, civ_sub_publisher_avatar)
        tv_nickname.text = info.NICKNAME
        tv_answer_content.text = info.CONTENT
        tv_sub_comment_time.text = info.CREATETIME
        tv_sub_comment_viewers_num.text = info.BROWSE.toString()
        tv_sub_comment_viewers_num.text = info.BROWSE.toString()
        tv_sub_comment_zan_num.text = info.PRAISE.toString()
        tv_reply.hint = "回复:${info.NICKNAME}"
        iv_sub_comment_zan.isSelected = info.IS_PRAISE == 1

        if (info.REPLY_LIST == null || info.REPLY_LIST.isEmpty()) {
            comments_view.visibility = View.GONE
        } else {
            comments_view.visibility = View.VISIBLE
            comments_view.setList(info.REPLY_LIST)
            comments_view.notifyDataSetChanged()
            comments_view.setOnItemClickListener(object : CommentsView.onItemClickListener {
                override fun onItemClick(position: Int, bean: CommentReplyBean?) {
                    if (bean != null) {
                        showReplyDialog(bean.PARENT, bean.USER_ID, bean.userName)
                    }
                }

            })
        }

        tv_reply.setOnClickListener {
            showReplyDialog(replyId, info.USER_ID, info.NICKNAME)
        }

        ll_sub_comment_zan.setOnClickListener {
            mPresenter?.praise(info.REPLY_ID)
        }

    }

    override fun loadDetailFail(needProgress: Boolean, errMsg: String) {
        if (needProgress) showError(errMsg)
    }


    override fun replySuccess() {
        EventBus.getDefault().post(UpdateTaxiuDetailCommentEvent())
        loadMoreComment(false)
        toastShow(true, "回复成功")
    }

    override fun replyFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun praiseSuccess(pos: Int, category: String) {
        iv_sub_comment_zan.isSelected = !iv_sub_comment_zan.isSelected
        var num = tv_sub_comment_zan_num.text.toString().toInt()
        tv_sub_comment_zan_num.text = if (iv_sub_comment_zan.isSelected) "${num + 1}" else "${num - 1}"
    }

    override fun praiseFail(errMsg: String) {
        toastShow(errMsg)
    }

    private fun showReplyDialog(replyId: Int, targetId: Int, replyUser: String) {
        ReplyDialog("回复:$replyUser", {
            //二级回复
            mPresenter?.reply(replyId, targetId, it)
        }).show(supportFragmentManager, MoreCommentActivity::class.java.name)
    }

}