package com.ipd.taxiu.presenter

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.TaxiuDetailBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.utils.CommentType
import com.ipd.taxiu.utils.comment.CommentApiFactory
import com.ipd.taxiu.utils.comment.ICommentApi

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