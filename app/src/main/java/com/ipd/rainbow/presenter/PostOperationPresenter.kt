package com.ipd.rainbow.presenter

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.TaxiuDetailBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.utils.CommentType
import com.ipd.rainbow.utils.comment.CommentApiFactory
import com.ipd.rainbow.utils.comment.ICommentApi

open class PostOperationPresenter<V : PostOperationPresenter.IPostOperationView> : BasePresenter<V, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    private var mType = CommentType.TAXIU
    val commentApi: ICommentApi by lazy { CommentApiFactory.createCommentApi(mType) }

    fun setType(type: Int) {
        mType = type
    }

    fun praise(category: String, categoryId: Int) {
        praise(-1, category, categoryId)
    }

    fun praise(pos: Int, category: String, categoryId: Int) {
        mModel?.getNormalRequestData(
                ApiManager.getService().taxiuPraise(GlobalParam.getUserIdOrJump(), category, categoryId),
                object : Response<BaseResult<TaxiuDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<TaxiuDetailBean>) {
                        if (result.code == 0) {
                            mView?.praiseSuccess(pos, category)
                        } else {
                            mView?.praiseFail(result.msg)
                        }
                    }
                })


    }


    interface IPostOperationView {
        fun praiseSuccess(pos: Int, category: String)
        fun praiseFail(errMsg: String)

    }
}