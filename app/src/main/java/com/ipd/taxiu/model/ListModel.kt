package com.ipd.taxiu.model

import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.platform.http.Response
import rx.Observable

/**
 * Created by jumpbox on 2017/5/24.
 */
class ListModel<T> : BaseModel() {

    fun getListData(observable: Observable<T>?, response: Response<T>?) {
        observable?.compose(RxScheduler.applyScheduler<T>())?.subscribe(response)
    }

}