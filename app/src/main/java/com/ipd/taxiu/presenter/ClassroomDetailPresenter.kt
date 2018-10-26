package com.ipd.taxiu.presenter.store

import com.ipd.taxiu.bean.*
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.presenter.BasePresenter

class ClassroomDetailPresenter : BasePresenter<ClassroomDetailPresenter.IClassroomDetailView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun loadDetail(classroomId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().classroomDetail(GlobalParam.getUserIdOrJump(), classroomId),
                object : Response<BaseResult<ClassRoomBean>>() {
                    override fun _onNext(result: BaseResult<ClassRoomBean>) {
                        if (result.code == 0) {
                            mView?.loadDetailSuccess(result.data)
                        } else {
                            mView?.loadDetailFail(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        mView?.loadDetailFail("连接服务器失败")
                    }
                })
    }


    fun toCollect(classroomId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().taxiuCollect(GlobalParam.getUserIdOrJump(), "3", classroomId),
                object : Response<BaseResult<TaxiuDetailBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<TaxiuDetailBean>) {
                        if (result.code == 0) {
                            mView?.collectSuccess()
                        } else {
                            mView?.collectFail(result.msg)
                        }
                    }
                })
    }


    fun wechat(classroomId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().classroomWechat(GlobalParam.getUserIdOrJump(), classroomId, 2),
                object : Response<ClassRoomResult<WechatBean>>(mContext, true) {
                    override fun _onNext(result: ClassRoomResult<WechatBean>) {
                        if (result.code == 0) {
                            mView?.classroomWechatSuccess(result.data, result.data2)
                        } else {
                            mView?.payFail(result.msg)
                        }
                    }
                })
    }

    fun alipay(classroomId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().classroomAlipay(GlobalParam.getUserIdOrJump(), classroomId, 1),
                object : Response<ClassRoomResult<String>>(mContext, true) {
                    override fun _onNext(result: ClassRoomResult<String>) {
                        if (result.code == 0) {
                            mView?.classroomAlipaySuccess(result.data, result.data2)
                        } else {
                            mView?.payFail(result.msg)
                        }
                    }
                })
    }

    fun balance(classroomId: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().classroomBalance(GlobalParam.getUserIdOrJump(), classroomId, 3),
                object : Response<ClassRoomResult<String>>(mContext, true) {
                    override fun _onNext(result: ClassRoomResult<String>) {
                        if (result.code == 0) {
                            mView?.classroomBalanceSuccess(result.data)
                        } else {
                            mView?.payFail(result.msg)
                        }
                    }
                })
    }


    interface IClassroomDetailView {
        fun loadDetailSuccess(detail: ClassRoomBean)
        fun loadDetailFail(errMsg: String)
        fun collectSuccess()
        fun collectFail(errMsg: String)
        fun classroomWechatSuccess(orderNo: String, wechatInfo: WechatBean)
        fun classroomAlipaySuccess(orderNo: String, info: String)
        fun classroomBalanceSuccess(orderNo: String)
        fun payFail(errMsg: String)
    }
}