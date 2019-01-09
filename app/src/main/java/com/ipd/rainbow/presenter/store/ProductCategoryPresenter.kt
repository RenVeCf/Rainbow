package com.ipd.rainbow.presenter.store

import com.ipd.rainbow.bean.*
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.presenter.BasePresenter

class ProductCategoryPresenter : BasePresenter<ProductCategoryPresenter.IProductCategoryView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun loadParentCategory(type: Int) {
        mModel?.getNormalRequestData(ApiManager.getService().storeParentShopType(GlobalParam.getUserIdOrJump(), type)
                , object : Response<BaseResult<List<ProductCategoryParentBean>>>() {
            override fun _onNext(result: BaseResult<List<ProductCategoryParentBean>>) {
                if (result.code == 0) {
                    mView?.onLoadParentCategorySuccess(result.data)
                } else {
                    mView?.onLoadParentCategoryFail(result.msg)
                }
            }

            override fun onError(e: Throwable?) {
                mView?.onLoadParentCategoryFail("连接服务器失败")
            }
        })
    }

    fun loadChildCategory(parentId: String) {
        mView?.onChildShowProgress()
        mModel?.getNormalRequestData(ApiManager.getService().storeChildShopType(GlobalParam.getUserIdOrJump(), parentId)
                , object : Response<BaseResult<ProductCategoryChildBean>>() {
            override fun _onNext(result: BaseResult<ProductCategoryChildBean>) {
                if (result.code == 0) {
                    val list: ArrayList<Any> = ArrayList()
                    list.add(ProductCategoryTitleBean("热销推荐"))
                    list.addAll(result.data.INFO_DATA.TIP_LIST)
                    //全部
                    list.add(ProductCategoryAllBean(0, result.data.TYPE_NAME))
                    list.add(ProductCategoryTitleBean("推荐品牌"))
                    list.addAll(result.data.INFO_DATA.BRAND_LIST)
                    //全部
                    list.add(ProductCategoryAllBean(1, result.data.TYPE_NAME))
                    mView?.onLoadChildCategorySuccess(list)
                } else {
                    mView?.onLoadParentCategoryFail(result.msg)
                }
            }

            override fun onError(e: Throwable?) {
                mView?.onLoadParentCategoryFail("连接服务器失败")
            }
        })
    }

    interface IProductCategoryView {
        fun onLoadParentCategorySuccess(list: List<ProductCategoryParentBean>)

        fun onLoadParentCategoryFail(errMsg: String)

        fun onChildShowProgress()

        fun onLoadChildCategorySuccess(list: List<Any>)

        fun onLoadChildCategoryFail(errMsg: String)
    }
}