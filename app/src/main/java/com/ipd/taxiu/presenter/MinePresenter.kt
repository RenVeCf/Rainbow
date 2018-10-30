package com.ipd.taxiu.presenter

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.OtherBean
import com.ipd.taxiu.bean.UpdatePwdBean
import com.ipd.taxiu.bean.UserBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response

/**
Created by Miss on 2018/8/17
 */
class MinePresenter<V> : BasePresenter<V, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
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

    fun updateUser(birthday: String, gender: Int, logo: String, nickname: String, pet_time: String,
                   tag: String, username: String) {
        if (mView !is IUpdateUserView) return
        val view = mView as IUpdateUserView

        mModel?.getNormalRequestData(ApiManager.getService().updateUser(birthday, gender, logo, nickname
                , pet_time, tag, username, GlobalParam.getUserId()),
                object : Response<BaseResult<UpdatePwdBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<UpdatePwdBean>) {
                        if (result.code == 0) {
                            view.updateUserSuccess()
                        } else {
                            view.updateUserFail(result.msg)
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
                object : Response<BaseResult<OtherBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<OtherBean>) {
                        if (result.code == 0) {
                            view.onGetOtherSuccess(result.data)
                        } else {
                            view.onGetOtherFail(result.msg)
                        }
                    }
                })
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
        fun onGetOtherSuccess(data: OtherBean)
        fun onGetOtherFail(errMsg: String)
    }
}