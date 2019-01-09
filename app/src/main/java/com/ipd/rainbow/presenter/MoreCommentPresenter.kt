package com.ipd.rainbow.presenter.store

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.MoreCommentReplyBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.presenter.PostOperationPresenter

class MoreCommentPresenter : PostOperationPresenter<MoreCommentPresenter.IMoreCommentView>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun loadMoreComment(needProgress: Boolean, replyId: Int) {
        mModel?.getNormalRequestData(commentApi.replyMore(GlobalParam.getUserIdOrJump(), replyId),
                object : Response<BaseResult<MoreCommentReplyBean>>() {
                    override fun _onNext(result: BaseResult<MoreCommentReplyBean>) {
                        if (result.code == 0) {
                            mView?.loadDetailSuccess(needProgress, result.data)
                        } else {
                            mView?.loadDetailFail(needProgress, result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadDetailFail(needProgress, "连接服务器失败")
                    }
                })
    }

    fun reply(replyId: Int, targetId: Int, content: String) {
        mModel?.getNormalRequestData(commentApi.secondReply(GlobalParam.getUserIdOrJump(), replyId, targetId, content),
                object : Response<BaseResult<MoreCommentReplyBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<MoreCommentReplyBean>) {
                        if (result.code == 0) {
                            mView?.replySuccess()
                        } else {
                            mView?.replyFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.replyFail("连接服务器失败")
                    }
                })
    }

    fun praise(categoryId: Int) {
        praise(commentApi.getPraiseReplyCategory(), categoryId)
    }


    interface IMoreCommentView : PostOperationPresenter.IPostOperationView {
        fun loadDetailSuccess(needProgress: Boolean, detail: MoreCommentReplyBean)
        fun loadDetailFail(needProgress: Boolean, errMsg: String)
        fun replySuccess()
        fun replyFail(errMsg: String)
    }
}