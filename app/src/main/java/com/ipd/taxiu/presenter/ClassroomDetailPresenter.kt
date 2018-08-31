package com.ipd.taxiu.presenter.store

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ClassRoomBean
import com.ipd.taxiu.bean.TaxiuDetailBean
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


    interface IClassroomDetailView {
        fun loadDetailSuccess(detail: ClassRoomBean)
        fun loadDetailFail(errMsg: String)
        fun collectSuccess()
        fun collectFail(errMsg: String)
    }
}