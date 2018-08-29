package com.ipd.taxiu.presenter.store

import com.ipd.taxiu.bean.AttentionBean
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.MoreCommentReplyBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.presenter.PostOperationPresenter

class TaxiuDetailChildPresenter : PostOperationPresenter<TaxiuDetailChildPresenter.ITaxiuDetailChildView>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun attention(ATTEN_ID: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().attention(ATTEN_ID, GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<AttentionBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<AttentionBean>) {
                        if (result.code == 0) {
                        } else {
                        }
                    }

                })
    }


    interface ITaxiuDetailChildView : PostOperationPresenter.IPostOperationView {
        fun attentionSuccess(detail: MoreCommentReplyBean)
        fun attentionFail(errMsg: String)
    }
}