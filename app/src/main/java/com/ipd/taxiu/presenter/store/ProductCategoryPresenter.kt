package com.ipd.taxiu.presenter.store

import com.ipd.taxiu.bean.ProductCategoryBean
import com.ipd.taxiu.bean.ProductCategoryChildBean
import com.ipd.taxiu.bean.ProductCategoryParentBean
import com.ipd.taxiu.bean.ProductCategoryTitleBean
import com.ipd.taxiu.model.BasicModel
import com.ipd.taxiu.presenter.BasePresenter

class ProductCategoryPresenter : BasePresenter<ProductCategoryPresenter.IProductCategoryView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    fun loadParentCategory(type: String) {
        val list = arrayListOf(ProductCategoryParentBean("狗粮"),
                ProductCategoryParentBean("零食"),
                ProductCategoryParentBean("保健"),
                ProductCategoryParentBean("清洁"),
                ProductCategoryParentBean("日用"),
                ProductCategoryParentBean("窝具"),
                ProductCategoryParentBean("服饰"),
                ProductCategoryParentBean("玩具")
        )
        mView?.onLoadParentCategorySuccess(list)

    }

    fun loadChildCategory(parentId: String, type: String) {
        val info = ProductCategoryChildBean()
        info.hotRecommend = ArrayList()
        info.brandRecommend = ArrayList()
        for (index: Int in 0 until 5) {
            info.hotRecommend.add(ProductCategoryBean())
            info.brandRecommend.add(ProductCategoryBean())
        }

        val list: ArrayList<Any> = ArrayList()
        list.add(ProductCategoryTitleBean("热销推荐"))
        list.addAll(info.hotRecommend)
        list.add(ProductCategoryTitleBean("推荐品牌"))
        list.addAll(info.brandRecommend)
        mView?.onLoadChildCategorySuccess(list)
    }

    interface IProductCategoryView {
        fun onLoadParentCategorySuccess(list: List<ProductCategoryParentBean>)

        fun onLoadParentCategoryFail(errMsg: String)

        fun onChildShowProgress()

        fun onLoadChildCategorySuccess(list: List<Any>)

        fun onLoadChildCategoryFail(errMsg: String)
    }
}