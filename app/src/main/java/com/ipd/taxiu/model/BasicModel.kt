package com.ipd.taxiu.model

import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.platform.http.Response
import rx.Observable

/**
 * Created by jumpbox on 2017/5/24.
 */
open class BasicModel : BaseModel() {

    fun <T> getNormalRequestData(observable: Observable<T>, response: Response<T>) {
        observable.compose(RxScheduler.applyScheduler<T>()).subscribe(response)
    }

}