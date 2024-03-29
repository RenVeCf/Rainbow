package com.ipd.rainbow.presenter

import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.UpdatePwdBean
import com.ipd.rainbow.bean.UserBean
import com.ipd.rainbow.bean.UserHomeBean
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response

/**
Created by Miss on 2018/8/17
 */
class MinePresenter<V> : BasePresenter<V, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun getUserHome() {
        if (mView !is IUserHomeView) return
        val view = mView as IUserHomeView

        mModel?.getNormalRequestData(ApiManager.getService().getUserHome(GlobalParam.getUserId()),
                object : Response<BaseResult<UserHomeBean>>() {
                    override fun _onNext(result: BaseResult<UserHomeBean>) {
                        if (result.code == 0) {
                            view.getInfoSuccess(result.data)
                        } else {
                            view.getInfoFail(result.msg)
                        }
                    }
                })
    }

    fun getUserInfo() {
        if (mView !is IUserInfoView) return
        val view = mView as IUserInfoView

        mModel?.getNormalRequestData(ApiManager.getService().getUserInfo(GlobalParam.getUserId()),
                object : Response<BaseResult<UserBean>>() {
                    override fun _onNext(result: BaseResult<UserBean>) {
                        if (result.code == 0) {
                            view.getInfoSuccess(result.data)
                        } else {
                            view.getInfoFail(result.msg)
                        }
                    }
                })
    }

    fun updatePwd(newPassword: String, oldPassword: String) {
        if (mView !is IUpdatePwdView) return
        val view = mView as IUpdatePwdView

        mModel?.getNormalRequestData(ApiManager.getService().updatePwd(GlobalParam.getUserId(), newPassword, oldPassword),
                object : Response<BaseResult<UpdatePwdBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<UpdatePwdBean>) {
                        if (result.code == 0) {
                            view.updateSuccess()
                        } else {
                            view.updateFail(result.msg)
                        }
                    }
                })
    }


    //关注或取消关注
    fun attention(attenId: Int) {
        if (mView !is IAttentionView) return
        val view = mView as IAttentionView

        if (attenId == 0) {
            view.onFail("关注（取消关注）用户ID不能为空!")
        }

        mModel?.getNormalRequestData(ApiManager.getService().attention(attenId, GlobalParam.getUserId()),
                object : Response<BaseResult<Int>>(mContext, true) {
                    override fun _onNext(result: BaseResult<Int>) {
                        if (result.code == 0) {
                            view.onSuccess(result.msg, result.data)
                        } else {
                            view.onFail(result.msg)
                        }
                    }
                })
    }

    fun other(otherUserId: Int) {
        if (mView !is IOtherView) return
        val view = mView as IOtherView

        mModel?.getNormalRequestData(ApiManager.getService().other(GlobalParam.getUserId(), otherUserId),
                object : Response<BaseResult<UserBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<UserBean>) {
                        if (result.code == 0) {
                            view.onGetOtherSuccess(result.data)
                        } else {
                            view.onGetOtherFail(result.msg)
                        }
                    }
                })
    }


    interface IUserHomeView {
        fun getInfoSuccess(data: UserHomeBean)
        fun getInfoFail(errMsg: String)
    }

    interface IUserInfoView {
        fun getInfoSuccess(data: UserBean)
        fun getInfoFail(errMsg: String)
    }

    interface IUpdatePwdView {
        fun updateSuccess()
        fun updateFail(errMsg: String)
    }

    interface IUpdateUserView {
        fun updateUserSuccess()
        fun updateUserFail(errMsg: String)
    }

    interface IAttentionView {
        fun onSuccess(msg: String, data: Int)
        fun onFail(errMsg: String)
    }

    interface IOtherView {
        fun onGetOtherSuccess(data: UserBean)
        fun onGetOtherFail(errMsg: String)
    }
}