package com.ipd.taxiu.ui.fragment.store

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIFragment
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_product_detail.view.*

class ProductDetailFragment : BaseUIFragment() {
    override fun getTitleLayout(): Int = -1
    override fun getContentLayout(): Int = R.layout.fragment_product_detail

    override fun initView(bundle: Bundle?) {
    }


    private var mTopFragment: ProductDetailTopFragment? = null
    private var mBottomFragment: ProductDetailBottomFragment? = null
    override fun loadData() {
        mContentView.view_pager.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                if (mTopFragment == null)
                    mTopFragment = ProductDetailTopFragment()
                if (mBottomFragment == null)
                    mBottomFragment = ProductDetailBottomFragment()
                return if (position == 0) mTopFragment!! else mBottomFragment!!
            }

            override fun getCount(): Int = 2

        }
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

}