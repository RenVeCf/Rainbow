package com.ipd.rainbow.presenter

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.TextBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response

/**
Created by Miss on 2018/8/21
 */
class TextPresenter<V>:BasePresenter<V,BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun getTextInfo(category:Int){
        if (mView !is ITextView) return
        val view = mView as ITextView

        mModel?.getNormalRequestData(ApiManager.getService().getTextInfo(category,GlobalParam.getUserId()),
                object : Response<BaseResult<TextBean>>(mContext,true){
                    override fun _onNext(result: BaseResult<TextBean>?) {
                        if (result?.code == 0){
                            view.onSuccess(result.data)
                        }else{
                            view.onFail(result!!.msg)
                        }
                    }

                })

    }

    interface ITextView{
        fun onSuccess(data:TextBean)
        fun onFail(errMsg:String)
    }
}