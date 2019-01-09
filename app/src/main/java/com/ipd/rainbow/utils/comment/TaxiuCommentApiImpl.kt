package com.ipd.rainbow.utils.comment

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.CommentDetailBean
import com.ipd.rainbow.bean.MoreCommentReplyBean
import com.ipd.rainbow.bean.TopicCommentReplyBean
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.utils.CommentType
import rx.Observable

class TaxiuCommentApiImpl : ICommentApi {
    override fun getPraiseReplyCategory(): String = CommentType.TAXIU_PRAISE_REPLY
    override fun getPraiseCommentCategory(): String = CommentType.TAXIU_PRAISE_COMMENT

    override fun commentDetail(userId: String, commentId: Int): Observable<BaseResult<CommentDetailBean>> {
        return ApiManager.getService().taxiuCommentDetail(userId, commentId)
    }

    override fun replyList(userId: String, pageSize: Int, page: Int, commentId: Int): Observable<BaseResult<List<TopicCommentReplyBean>>> {
        return ApiManager.getService().taxiuReplyList(userId, pageSize, page, commentId)
    }

    override fun replyMore(userId: String, replyId: Int): Observable<BaseResult<MoreCommentReplyBean>> {
        return ApiManager.getService().taxiuReplyMore(userId, replyId)
    }

    override fun firstReply(userId: String, commentId: Int, content: String): Observable<BaseResult<MoreCommentReplyBean>> {
        return ApiManager.getService().taxiuFirstReply(userId, commentId, content)
    }

    override fun secondReply(userId: String, replyId: Int, targetId: Int, content: String): Observable<BaseResult<MoreCommentReplyBean>> {
        return ApiManager.getService().taxiuSecondReply(userId, replyId, targetId, content)
    }

}