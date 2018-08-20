package com.ipd.taxiu.presenter

import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.PetBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response

/**
Created by Miss on 2018/8/18
 */
class PetPresenter<V> : BasePresenter<V, BasicModel>() {

    override fun initModel() {
        mModel = BasicModel()
    }

    fun petGetInfo(petId: Int) {
        if (mView !is IPetInfoView) return
        var view = mView as IPetInfoView

        mModel?.getNormalRequestData(ApiManager.getService().petGetInfo(petId,GlobalParam.getUserId()),
                object : Response<BaseResult<PetBean>>(mContext,true){
                    override fun _onNext(result: BaseResult<PetBean>?) {
                        if (result?.code == 0) {
                            view.getInfoSuccess(result.data)
                        } else {
                            view.getInfoFail(result!!.msg)
                        }
                    }

                })
    }

    fun petDelete(petId: Int) {
        if (mView !is IPetDeleteView) return
        var view = mView as IPetDeleteView

        mModel?.getNormalRequestData(ApiManager.getService().petDelete(petId,GlobalParam.getUserId()),
                object : Response<BaseResult<PetBean>>(mContext,false){
                    override fun _onNext(result: BaseResult<PetBean>?) {
                        if (result?.code == 0) {
                            view.deleteSuccess()
                        } else {
                            view.deleteFail(result!!.msg)
                        }
                    }

                })
    }

    fun petUpdate(birthday:String,gender:Int,logo:String,nickname:String,pet_type_id:Int,status:Int,petId: Int) {
        if (mView !is IPetUpdateView) return
        var view = mView as IPetUpdateView

        if (logo == ""){
            view.updateFail("头像不能为空！")
            return
        }
        if (nickname == ""){
            view.updateFail("昵称不能为空！")
            return
        }
        if (birthday == ""){
            view.updateFail("生日不能为空！")
            return
        }
        if (gender == 0){
            view.updateFail("性别不能为空！")
            return
        }

        if (pet_type_id == 0){
            view.updateFail("宠物种类ID不能为空！")
            return
        }
        if (status == 0){
            view.updateFail("状态不能为空！")
            return
        }
        if (petId == 0){
            view.updateFail("宠物ID不能为空！")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().petUpdate(birthday,gender,logo,nickname,
                pet_type_id,status,petId,GlobalParam.getUserId()),
                object : Response<BaseResult<PetBean>>(mContext,true){
                    override fun _onNext(result: BaseResult<PetBean>?) {
                        if (result?.code == 0) {
                            view.updateSuccess()
                        } else {
                            view.updateFail(result!!.msg)
                        }
                    }

                })
    }

    fun petAdd(birthday:String,gender:Int,logo:String,nickname:String,pet_type_id:Int,status:Int) {
        if (mView !is IPetAddView) return
        var view = mView as IPetAddView

        if (logo == ""){
            view.addFail("头像不能为空！")
            return
        }
        if (nickname == ""){
            view.addFail("昵称不能为空！")
            return
        }
        if (birthday == ""){
            view.addFail("生日不能为空！")
            return
        }
        if (gender == 0){
            view.addFail("性别不能为空！")
            return
        }

        if (pet_type_id == 0){
            view.addFail("宠物种类ID不能为空！")
            return
        }
        if (status == 0){
            view.addFail("状态不能为空！")
            return
        }

        mModel?.getNormalRequestData(ApiManager.getService().petAdd(birthday,gender,logo,nickname,
                pet_type_id,status,GlobalParam.getUserId()),
                object : Response<BaseResult<PetBean>>(mContext,true){
                    override fun _onNext(result: BaseResult<PetBean>?) {
                        if (result?.code == 0) {
                            view.addSuccess()
                        } else {
                            view.addFail(result!!.msg)
                        }
                    }

                })
    }

    interface IPetInfoView{
        fun getInfoSuccess(data : PetBean)
        fun getInfoFail(errMsg : String)
    }
    interface IPetUpdateView{
        fun updateSuccess()
        fun updateFail(errMsg : String)
    }
    interface IPetAddView{
        fun addSuccess()
        fun addFail(errMsg : String)
    }
    interface IPetDeleteView{
        fun deleteSuccess()
        fun deleteFail(errMsg : String)
    }
}