package com.ipd.taxiu.ui.fragment.store

import android.graphics.Paint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewTreeObserver
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.BannerPagerAdapter
import com.ipd.taxiu.adapter.PackageProductAdapter
import com.ipd.taxiu.bean.BannerBean
import com.ipd.taxiu.bean.ProductDetailBean
import com.ipd.taxiu.ui.BaseUIFragment
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import com.ipd.taxiu.utils.IndicatorHelper
import com.ipd.taxiu.utils.StoreType
import com.ipd.taxiu.utils.StringUtils
import com.ipd.taxiu.widget.ProductCouponDialog
import kotlinx.android.synthetic.main.fragment_product_detail_top.view.*
import kotlinx.android.synthetic.main.layout_option_package.view.*
import kotlinx.android.synthetic.main.layout_store_banner.view.*

class ProductDetailTopFragment : BaseUIFragment() {
    override fun getTitleLayout(): Int = -1

    override fun getContentLayout(): Int = R.layout.fragment_product_detail_top

    private lateinit var mProductInfo: ProductDetailBean
    fun setDetailData(info: ProductDetailBean) {
        mProductInfo = info
    }

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
        val bannerList = StringUtils.splitImages(mProductInfo.PIC).map { BannerBean(it) }
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

        when (mProductInfo.KIND) {
            StoreType.PRODUCT_NORMAL -> {
                mContentView.ll_price.visibility = View.VISIBLE
                mContentView.layout_option_package.visibility = View.GONE

            }
            StoreType.PRODUCT_GROUP_PRODUCT -> {
                mContentView.ll_price.visibility = View.VISIBLE

                //可选套餐
                mContentView.layout_option_package.visibility = View.VISIBLE
                mContentView.package_recycler_view.layoutManager = LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false)
                mContentView.package_recycler_view.adapter = PackageProductAdapter(mActivity, mProductInfo.GROUP_LIST, {
                    ProductDetailActivity.launch(mActivity, it.PRODUCT_ID, it.FORM_ID)
                })
            }
            else -> {
                mContentView.ll_price.visibility = View.GONE
                mContentView.layout_option_package.visibility = View.GONE

            }
        }


        mContentView.rl_product_extra.setProductInfo(mProductInfo)
        mContentView.tv_product_name.text = mProductInfo.PROCUCT_NAME
        mContentView.tv_price.text = mProductInfo.CURRENT_PRICE
        mContentView.tv_old_price.text = "￥${mProductInfo.REFER_PRICE}"

        if (mProductInfo.POST_FEE == 0) {
            mContentView.tv_express_fee.text = "快递：免运费"
        } else {
            mContentView.tv_express_fee.text = "快递：￥${mProductInfo.POST_FEE}"
        }
        mContentView.tv_sales.text = "月销${mProductInfo.BUYNUM}件"
        mContentView.tv_ship_address.text = mProductInfo.SEND_CITY
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