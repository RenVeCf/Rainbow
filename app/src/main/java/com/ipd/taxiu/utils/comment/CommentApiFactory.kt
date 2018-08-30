package com.ipd.taxiu.utils.comment

import com.ipd.taxiu.utils.CommentType

object CommentApiFactory {
    fun createCommentApi(type: Int): ICommentApi {
        return when (type) {
            CommentType.TAXIU -> {
                return TaxiuCommentApiImpl()
            }
            CommentType.TALK -> {
                return TalkCommentApiImpl()
            }
            else -> {
                return TopicCommentApiImpl()
            }
        }
    }
}