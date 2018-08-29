package com.ipd.taxiu.utils.comment

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.CommentDetailBean
import com.ipd.taxiu.bean.MoreCommentReplyBean
import com.ipd.taxiu.bean.TopicCommentReplyBean
import rx.Observable

interface ICommentApi {
    fun commentDetail(userId: String, commentId: Int): Observable<BaseResult<CommentDetailBean>>
    fun replyList(userId: String, pageSize: Int, page: Int, commentId: Int): Observable<BaseResult<List<TopicCommentReplyBean>>>
    fun replyMore(userId: String, replyId: Int): Observable<BaseResult<MoreCommentReplyBean>>
    fun firstReply(userId: String, commentId: Int, content: String): Observable<BaseResult<MoreCommentReplyBean>>
    fun secondReply(userId: String, replyId: Int, targetId: Int, content: String): Observable<BaseResult<MoreCommentReplyBean>>
    fun getPraiseReplyCategory(): String
    fun getPraiseCommentCategory(): String
}