package com.ipd.taxiu.presenter.store

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.MoreCommentReplyBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.presenter.PostOperationPresenter

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