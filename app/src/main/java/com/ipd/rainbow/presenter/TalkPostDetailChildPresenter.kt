package com.ipd.rainbow.presenter

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.MoreCommentReplyBean
import com.ipd.rainbow.bean.TalkBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler

class TalkPostDetailChildPresenter : PostOperationPresenter<TalkPostDetailChildPresenter.ITalkPostDetailChildView>() {

    override fun initModel() {
        mModel = BasicModel()
    }

    fun attention(ATTEN_ID: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().attention(ATTEN_ID, GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<Int>>(mContext, true) {
                    override fun _onNext(result: BaseResult<Int>) {
                        if (result.code == 0) {
                            mView?.attentionSuccess(result.data)
                        } else {
                            mView?.attentionFail(result.msg)
                        }
                    }

                })
    }

    fun firstReply(replyId: Int, content: String) {
        commentApi.firstReply(GlobalParam.getUserIdOrJump(), replyId, content)
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<MoreCommentReplyBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<MoreCommentReplyBean>) {
                        if (result.code == 0) {
                            mView?.replySuccess()
                        } else {
                            mView?.replyFail(result.msg)
                        }
                    }
                })
    }

    fun secondReply(replyId: Int, targetId: Int, content: String) {
        commentApi.secondReply(GlobalParam.getUserIdOrJump(), replyId, targetId, content)
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<MoreCommentReplyBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<MoreCommentReplyBean>) {
                        if (result.code == 0) {
                            mView?.replySuccess()
                        } else {
                            mView?.replyFail(result.msg)
                        }
                    }
                })
    }

    fun bestAnswer(questionId: Int, answerId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().bestAnswer(GlobalParam.getUserIdOrJump(), questionId, answerId),
                object : Response<BaseResult<TalkBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<TalkBean>) {
                        if (result.code == 0) {
                            mView?.bestAnswerSuccess()
                        } else {
                            mView?.bestAnswerFail(result.msg)
                        }
                    }
                })
    }


    interface ITalkPostDetailChildView : PostOperationPresenter.IPostOperationView {
        fun attentionSuccess(isAttent: Int)
        fun attentionFail(errMsg: String)
        fun replySuccess()
        fun replyFail(errMsg: String)
        fun bestAnswerSuccess()
        fun bestAnswerFail(errMsg: String)
    }

}