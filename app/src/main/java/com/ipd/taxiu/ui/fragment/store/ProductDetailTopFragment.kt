package com.ipd.taxiu.ui.fragment.store

import android.graphics.Paint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewTreeObserver
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.BannerPagerAdapter
import com.ipd.taxiu.adapter.PackageProductAdapter
import com.ipd.taxiu.bean.BannerBean
import com.ipd.taxiu.bean.TalkBean
import com.ipd.taxiu.ui.BaseUIFragment
import com.ipd.taxiu.utils.IndicatorHelper
import com.ipd.taxiu.widget.ProductCouponDialog
import kotlinx.android.synthetic.main.fragment_product_detail_top.view.*
import kotlinx.android.synthetic.main.layout_option_package.view.*
import kotlinx.android.synthetic.main.layout_store_banner.view.*

class ProductDetailTopFragment : BaseUIFragment() {
    override fun getTitleLayout(): Int = -1

    override fun getContentLayout(): Int = R.layout.fragment_product_detail_top

    override fun initView(bundle: Bundle?) {
        mContentView.tv_old_price.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        mContentView.store_banner.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                mContentView.store_banner.viewTreeObserver.removeGlobalOnLayoutListener(this)
                val params = mContentView.store_banner.layoutParams
                params.height = mContentView.store_banner.measuredWidth
                mContentView.store_banner.requestLayout()

            }
        })
    }

    override fun loadData() {
        val bannerList: List<BannerBean> = arrayListOf(BannerBean(R.mipmap.product_img), BannerBean(R.mipmap.product_img), BannerBean(R.mipmap.product_img))
        mContentView.store_banner.adapter = BannerPagerAdapter(context, bannerList)
        IndicatorHelper.newInstance().setRes(R.mipmap.boutique_selected, R.mipmap.boutique_unselected)
                .setIndicator(context, bannerList.size, mContentView.store_banner, mContentView.store_banner_indicator, object : IndicatorHelper.MyPagerChangeListener {
                    override fun onPageSelected(pos: Int) {
                    }

                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                    }

                })
        mContentView.store_banner.startAutoScroll()


        mContentView.package_recycler_view.layoutManager = LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false)
        mContentView.package_recycler_view.adapter = PackageProductAdapter(mActivity, listOf(TalkBean(), TalkBean(), TalkBean(),TalkBean()), {

        })
    }

    override fun initListener() {
        mContentView.ll_coupon.setOnClickListener {
            //领券
            ProductCouponDialog(mActivity).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mContentView.store_banner.stopAutoScroll()
    }

}