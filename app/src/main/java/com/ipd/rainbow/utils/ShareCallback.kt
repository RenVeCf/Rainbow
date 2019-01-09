package com.ipd.rainbow.utils

import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.RxScheduler

object ShareCallback {

    fun shareTaxiu(showId: Int) {
        ApiManager.getService().shareTaxiu(GlobalParam.getUserId(), showId)
                .compose(RxScheduler.applyScheduler())
                .subscribe()
    }

    fun shareTopic(topic: Int) {
        ApiManager.getService().shareTopic(GlobalParam.getUserId(), topic)
                .compose(RxScheduler.applyScheduler())
                .subscribe()
    }

    fun shareTalk(talkId: Int) {
        ApiManager.getService().shareTalk(GlobalParam.getUserId(), talkId)
                .compose(RxScheduler.applyScheduler())
                .subscribe()
    }

    fun shareClassroom(classroomId: Int) {
        ApiManager.getService().shareClassroom(GlobalParam.getUserId(), classroomId)
                .compose(RxScheduler.applyScheduler())
                .subscribe()
    }

    fun shareVideo(videoId: Int) {
        ApiManager.getService().shareVideo(GlobalParam.getUserId(), videoId)
                .compose(RxScheduler.applyScheduler())
                .subscribe()
    }

    fun shareProduct(productId: Int, formId: Int) {
        ApiManager.getService().shareProduct(GlobalParam.getUserId(), productId, formId)
                .compose(RxScheduler.applyScheduler())
                .subscribe()
    }

    fun shareUser() {
        ApiManager.getService().shareUser(GlobalParam.getUserId())
                .compose(RxScheduler.applyScheduler())
                .subscribe()
    }
}