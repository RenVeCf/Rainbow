package com.ipd.taxiu.ui.fragment.store

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ProductDetailBean
import com.ipd.taxiu.presenter.store.StoreProductDetailPresenter
import com.ipd.taxiu.ui.BaseUIFragment
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_product_detail.view.*

class ProductDetailFragment : BaseUIFragment(), StoreProductDetailPresenter.IStoreProductDetailView {

    companion object {
        fun newInstance(productId: Int, formId: Int): ProductDetailFragment {
            val fragment = ProductDetailFragment()
            val bundle = Bundle()
            bundle.putInt("productId", productId)
            bundle.putInt("formId", formId)
            fragment.arguments = bundle
            return fragment
        }
    }


    private val mProductId by lazy { arguments.getInt("productId", -1) }
    private val mFromId by lazy { arguments.getInt("formId", -1) }

    override fun getTitleLayout(): Int = -1
    override fun getContentLayout(): Int = R.layout.fragment_product_detail


    private var mPresenter: StoreProductDetailPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = StoreProductDetailPresenter()
        mPresenter?.attachView(mActivity, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }

    override fun initView(bundle: Bundle?) {
    }


    private var mTopFragment: ProductDetailTopFragment? = null
    private var mBottomFragment: ProductDetailBottomFragment? = null
    override fun loadData() {
        showProgress()
        mPresenter?.loadProductDetail(mProductId, mFromId)
    }

    override fun initListener() {
        mContentView.view_pager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (mActivity is ProductDetailActivity)
                    (mActivity as ProductDetailActivity).switchTab(position)
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }

    fun switchPage(pos: Int) {
        mContentView?.view_pager?.setCurrentItem(pos, false)
    }

    override fun loadProductDetailSuccess(info: ProductDetailBean) {
        showContent()
        mContentView.view_pager.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                if (mTopFragment == null) {
                    mTopFragment = ProductDetailTopFragment()
                    mTopFragment?.setDetailData(info)
                }
                if (mBottomFragment == null) {
                    mBottomFragment = ProductDetailBottomFragment()
                    mBottomFragment?.setDetailData(info)
                }
                return if (position == 0) mTopFragment!! else mBottomFragment!!
            }

            override fun getCount(): Int = 2

        }
    }

    override fun loadProductDetailFail(errMsg: String) {
        showError(errMsg)
    }

}