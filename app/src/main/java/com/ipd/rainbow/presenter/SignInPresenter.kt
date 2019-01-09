package com.ipd.rainbow.presenter.store

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.SignInBean
import com.ipd.rainbow.bean.SignInResuleBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.presenter.BasePresenter
import rx.Observable

class SignInPresenter : BasePresenter<SignInPresenter.ISignInView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun loadSignInInfo() {
        val signInInfo = ApiManager.getService().signInInfo(GlobalParam.getUserId())
        val signInList = ApiManager.getService().signInList(GlobalParam.getUserId())

        val zip = Observable.zip(signInInfo,
                signInList
        ) { t1, t2 ->
            val baseResult = BaseResult<SignInBean>()
            baseResult.data = SignInBean()
            if (t1.code != 0) {
                baseResult.code = t1.code
                baseResult.msg = t1.msg
                baseResult
            } else {
                if (t2.code == 0 || t2.code == 10000) {
                    baseResult.code = 0
                    if (t1.data != null) baseResult.data.signInfo = t1.data
                    if (t2.data != null) baseResult.data.signInDayList = t2.data
                } else {
                    baseResult.code = t2.code
                    baseResult.msg = t2.msg
                }
                baseResult
            }
        }

        mModel?.getNormalRequestData(zip,
                object : Response<BaseResult<SignInBean>>() {
                    override fun _onNext(result: BaseResult<SignInBean>) {
                        if (result.code == 0) {
                            mView?.loadSignInInfoSuccess(result.data)
                        } else {
                            mView?.loadSignInInfoFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadSignInInfoFail("连接服务器失败")
                    }

                })


    }

    fun signIn() {
        mModel?.getNormalRequestData(ApiManager.getService().signIn(GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<SignInResuleBean>>() {
                    override fun _onNext(result: BaseResult<SignInResuleBean>) {
                        if (result.code == 0) {
                            mView?.signInSuccess(result.data.SCORE)
                        } else {
                            mView?.signInFail(result.msg)
                        }
                    }

                })
    }


    interface ISignInView {
        fun loadSignInInfoSuccess(signInBean: SignInBean)
        fun loadSignInInfoFail(errMsg: String)
        fun signInSuccess(score: Int)
        fun signInFail(errMsg: String)
    }
}