package com.ipd.taxiu.ui.activity.topic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.CommentDetailBean
import com.ipd.taxiu.bean.MoreCommentReplyBean
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.topic.TopicPeopleCommentFragment
import com.ipd.taxiu.utils.comment.CommentApiFactory
import com.ipd.taxiu.utils.comment.ICommentApi
import com.ipd.taxiu.widget.ReplyDialog
import kotlinx.android.synthetic.main.activity_topic_people_recommend.*

class TopicPeopleCommentActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, title: String = "某某人", type: Int = 0, commentId: Int = -1) {
            val intent = Intent(activity, TopicPeopleCommentActivity::class.java)
            intent.putExtra("type", type)
            intent.putExtra("commentId", commentId)
            intent.putExtra("title", title)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "${intent.getStringExtra("title")}的评论"

    override fun getContentLayout(): Int = R.layout.activity_topic_people_recommend


    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    private val commentApi: ICommentApi by lazy { CommentApiFactory.createCommentApi(type) }
    val type by lazy { intent.getIntExtra("type", 0) }
    val commentId by lazy { intent.getIntExtra("commentId", -1) }
    val fragment by lazy { TopicPeopleCommentFragment.newInstance(type, commentId) }
    override fun loadData() {
        showProgress()
        commentApi.commentDetail(GlobalParam.getUserIdOrJump(), commentId).compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<CommentDetailBean>>() {
                    override fun _onNext(result: BaseResult<CommentDetailBean>) {
                        if (result.code == 0) {
                            showContent()
                            fragment.setData(result.data)
                            supportFragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit()
                        } else {
                            showError(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        showError("连接服务器失败")
                    }
                })
    }

    override fun initListener() {
        tv_reply.setOnClickListener {
            //一级回复
            ReplyDialog(tv_reply.hint.toString(), {
                commentApi.firstReply(GlobalParam.getUserIdOrJump(), commentId, it)
                        .compose(RxScheduler.applyScheduler())
                        .subscribe(object : Response<BaseResult<MoreCommentReplyBean>>(mActivity, true) {
                            override fun _onNext(result: BaseResult<MoreCommentReplyBean>) {
                                if (result.code == 0) {
                                    toastShow(true, "评论成功")
                                    fragment.refreshUI()
                                } else {
                                    toastShow(result.msg)
                                }
                            }
                        })

            }).show(supportFragmentManager, TopicPeopleCommentActivity::class.java.name)
        }

    }

}