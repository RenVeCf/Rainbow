package com.ipd.rainbow.presenter.store

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.presenter.PostOperationPresenter

open class PostDetailChildPresenter : PostOperationPresenter<PostDetailChildPresenter.ITaxiuDetailChildView>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun attention(ATTEN_ID: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().attention(ATTEN_ID, GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<Int>>(mContext, true) {
                    override fun _onNext(result: BaseResult<Int>) {
                        if (result.code == 0) {
                            mView?.attentionSuccess(result.data)
                        } else {
                            mView?.attentionFail(result.msg)
                        }
                    }

                })
    }


    interface ITaxiuDetailChildView : PostOperationPresenter.IPostOperationView {
        fun attentionSuccess(isAttent: Int)
        fun attentionFail(errMsg: String)
    }
}