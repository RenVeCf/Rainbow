package com.ipd.taxiu.presenter

import android.util.Log
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom
import com.ipd.taxiu.bean.*
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
                object : Response<BaseResult<UserBean>>(mContext, true) {
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
    fun attention(attenId:Int) {
        if (mView !is IAttentionView) return
        val view = mView as IAttentionView

        if (attenId == 0){
            view.onFail("关注（取消关注）用户ID不能为空!")
        }

        mModel?.getNormalRequestData(ApiManager.getService().attention(attenId,GlobalParam.getUserId()),
                object : Response<BaseResult<AttentionBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<AttentionBean>) {
                        if (result.code == 0) {
                            view.onSuccess(result.msg)
                        } else {
                            view.onFail(result.msg)
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
        fun onSuccess(msg : String)
        fun onFail(errMsg: String)
    }
}