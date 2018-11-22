package com.ipd.taxiu.utils

import android.content.Context
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.WebBean
import com.ipd.taxiu.platform.global.GlobalApplication
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import rx.Observable

/**
 * Created by jumpbox on 2018/6/27.
 */
object WebUtils {
    fun getWebInfo(context: Context, observable: Observable<BaseResult<WebBean>>, callback: (info: WebBean) -> Unit) {
        observable.compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<WebBean>>(context, true) {
                    override fun _onNext(result: BaseResult<WebBean>) {
                        when (result.code) {
                            200 -> {
                                callback.invoke(result.data)
                            }
                            else -> {
                                ToastCommom.getInstance().show(GlobalApplication.mContext, result.msg)
                            }
                        }
                    }
                })
    }


    fun getHtmlData(bodyHTML: String): String {
        val head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width:100% !important; width:auto; height:auto;}</style>" +
                "</head>"
        return "<html>$head<body style:'height:auto;max-width: 100%; width:auto;'>$bodyHTML</body></html>"
    }

}