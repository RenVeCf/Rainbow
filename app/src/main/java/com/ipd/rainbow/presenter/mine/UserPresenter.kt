package com.ipd.rainbow.presenter.mine

import android.text.TextUtils
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.UserBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.presenter.BasePresenter

class UserPresenter : BasePresenter<UserPresenter.IUserView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun loadUserInfo() {
        mModel?.getNormalRequestData(
                ApiManager.getService().getUserInfo(GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<UserBean>>() {
                    override fun _onNext(result: BaseResult<UserBean>) {
                        if (result.code == 0) {
                            mView?.loadUserInfoSuccess(result.data)
                        } else {
                            mView?.loadUserInfoFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadUserInfoFail(
                                mContext?.resources?.getString(R.string.loading_error)
                                        ?: "连接服务器失败"
                        )
                    }

                })
    }

    fun updateUserInfo(
            avatar: String = "",
            username: String = "",
            sex: String = ""
    ) {
        if (TextUtils.isEmpty(username)) {
            mView?.updateUserInfoFail("请输入您的真实姓名")
            return
        } else if (TextUtils.isEmpty(sex)) {
            mView?.updateUserInfoFail("请选择您的性别")
            return
        }

        val sexParam = if (sex == "男") "0" else "1"

        mModel?.getNormalRequestData(ApiManager.getService().updateUser(
                sexParam,
                avatar,
                username,
                GlobalParam.getUserIdOrJump()
        ),
                object : Response<BaseResult<UserBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<UserBean>) {
                        if (result.code == 0) {
                            mView?.updateUserInfoSuccess()
                        } else {
                            mView?.updateUserInfoFail(result.msg)
                        }
                    }

                })
    }


    interface IUserView {
        fun loadUserInfoSuccess(info: UserBean)
        fun loadUserInfoFail(errMsg: String)
        fun updateUserInfoSuccess()
        fun updateUserInfoFail(errMsg: String)
    }


}