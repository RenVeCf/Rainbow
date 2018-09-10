package com.ipd.taxiu.presenter

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ShowPetBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response

/**
Created by Miss on 2018/9/7
 */
class ShowPetPresenter<V> : BasePresenter<V,BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }

    fun showPet(){
        if (mView !is IShowPetView) return
        var view = mView as IShowPetView
        mModel?.getNormalRequestData(ApiManager.getService().taxiuShowPet(GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<ShowPetBean>>(mContext,true){
                    override fun _onNext(result: BaseResult<ShowPetBean>?) {
                        if (result?.code == 0){
                            view.getSuccess(result.data)
                        }else{
                            view.getFail(result!!.msg)
                        }
                    }

                })
    }

    interface IShowPetView{
        fun getSuccess(data:ShowPetBean)
        fun getFail(errMsg:String)
    }
}