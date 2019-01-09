package com.ipd.rainbow.utils

import com.ipd.rainbow.platform.global.Constant
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class TimeCountHelper private constructor(){
    companion object {
        fun newInstance(): TimeCountHelper {
            return TimeCountHelper()
        }
    }

    private var mTimeCountListener: TimeCountListener? = null
    private var mStartTimeSecond = Constant.SMS_CODE_TIME

    private var smsSubscriber: Subscriber<Long>? = null
    fun start() {
        smsSubscriber?.unsubscribe()
        smsSubscriber = object : Subscriber<Long>() {
            override fun onCompleted() {
                unsubscribe()
                mTimeCountListener?.onFinish()
            }

            override fun onError(e: Throwable) {

            }

            override fun onNext(aLong: Long) {
                if (aLong <= 0) {
                    onCompleted()
                } else {
                    mTimeCountListener?.onChange(aLong)
                }
            }
        }
        Observable.interval(0, 1000, TimeUnit.MILLISECONDS).map { aLong -> mStartTimeSecond - aLong!! }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(smsSubscriber)

    }

    fun release() {
        smsSubscriber?.unsubscribe()
    }


    fun setTimeCountListener(timeCountListener: TimeCountListener): TimeCountHelper {
        mTimeCountListener = timeCountListener
        return this
    }

    fun setStartTimeSecond(time: Int): TimeCountHelper {
        mStartTimeSecond = time
        return this
    }


    interface TimeCountListener {
        fun onChange(aLong: Long)
        fun onFinish()
    }


}