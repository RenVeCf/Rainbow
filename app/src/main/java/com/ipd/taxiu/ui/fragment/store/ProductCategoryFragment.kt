package com.ipd.taxiu.ui.fragment.store

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ParentCategoryAdapter
import com.ipd.taxiu.adapter.ProductCategoryAdapter
import com.ipd.taxiu.bean.ProductCategoryParentBean
import com.ipd.taxiu.bean.ProductCategoryTitleBean
import com.ipd.taxiu.presenter.store.ProductCategoryPresenter
import com.ipd.taxiu.ui.BaseFragment
import com.ipd.taxiu.ui.activity.store.ProductCategoryActivity
import kotlinx.android.synthetic.main.fragment_product_category.view.*

class ProductCategoryFragment : BaseFragment(), ProductCategoryPresenter.IProductCategoryView {


    companion object {
        fun newInstance(type: Int): ProductCategoryFragment {
            val fragment = ProductCategoryFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }


    private val mType by lazy { arguments.getInt("type") }
    override fun getBaseLayout(): Int = R.layout.fragment_product_category

    private var mPresenter: ProductCategoryPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = ProductCategoryPresenter()
        mPresenter?.attachView(mActivity, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }

    override fun initView(bundle: Bundle?) {

    }

    override fun loadData() {
        mPresenter?.loadParentCategory(mType + 1)
    }

    override fun initListener() {
    }

    private var mParentCategoryAdapter: ParentCategoryAdapter? = null
    override fun onLoadParentCategorySuccess(list: List<ProductCategoryParentBean>) {
        mRootView!!.parent_category_view.layoutManager = LinearLayoutManager(mActivity)
        mParentCategoryAdapter = ParentCategoryAdapter(mActivity, list)
        mRootView!!.parent_category_view.adapter = mParentCategoryAdapter

        mParentCategoryAdapter?.setItemCheckedListener { position ->
            mRootView!!.parent_category_view.scrollCenterByPosition(position)
            mPresenter?.loadChildCategory(mParentCategoryAdapter?.curCheckedCategoryId
                    ?: "0")
        }
        mPresenter?.loadChildCategory(mParentCategoryAdapter?.curCheckedCategoryId
                ?: "0")
    }

    override fun onLoadParentCategoryFail(errMsg: String) {

    }

    override fun onChildShowProgress() {
        mRootView!!.child_progress_layout.showLoading()
    }

    override fun onLoadChildCategorySuccess(list: List<Any>) {
        mRootView!!.child_progress_layout.showContent()
        val gridLayoutManager = mRootView!!.child_category_view.layoutManager as GridLayoutManager
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (list[position] is ProductCategoryTitleBean) 3 else 1
            }

        }
        mRootView!!.child_category_view.adapter = ProductCategoryAdapter(mActivity, list,{
            if (mActivity is ProductCategoryActivity){
                (mActivity as ProductCategoryActivity).switchToBrand()
            }
        })


    }

    override fun onLoadChildCategoryFail(errMsg: String) {
        mRootView!!.child_progress_layout.showError(errMsg, {
            mPresenter?.loadChildCategory(mParentCategoryAdapter?.curCheckedCategoryId
                    ?: "0")
        })
    }

}