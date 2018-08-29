package com.ipd.taxiu.presenter

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.MoreCommentReplyBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler

class TalkPostDetailChildPresenter : PostOperationPresenter<TalkPostDetailChildPresenter.ITalkPostDetailChildView>() {

    override fun initModel() {
        mModel = BasicModel()
    }

    fun attention(ATTEN_ID: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().attention(ATTEN_ID, GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<Int>>(mContext, true) {
                    override fun _onNext(result: BaseResult<Int>) {
                        if (result.code == 0) {
                        } else {
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


    interface ITalkPostDetailChildView : PostOperationPresenter.IPostOperationView {
        fun attentionSuccess(detail: MoreCommentReplyBean)
        fun attentionFail(errMsg: String)
        fun replySuccess()
        fun replyFail(errMsg: String)
    }

}