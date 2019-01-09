package com.ipd.rainbow.model

import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.platform.http.Response
import rx.Observable

/**
 * Created by jumpbox on 2017/5/24.
 */
class ListModel<T> : BaseModel() {

    fun getListData(observable: Observable<T>?, response: Response<T>?) {
        observable?.compose(RxScheduler.applyScheduler<T>())?.subscribe(response)
    }

}