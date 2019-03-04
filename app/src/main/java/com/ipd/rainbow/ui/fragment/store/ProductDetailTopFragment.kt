package com.ipd.rainbow.ui.fragment.store

import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewTreeObserver
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.BannerPagerAdapter
import com.ipd.rainbow.adapter.ProductDetailEvaluateAdapter
import com.ipd.rainbow.bean.BannerBean
import com.ipd.rainbow.bean.ProductDetailBean
import com.ipd.rainbow.ui.BaseUIFragment
import com.ipd.rainbow.ui.activity.PictureAndVideoPreviewActivity
import com.ipd.rainbow.ui.activity.store.ProductDetailActivity
import com.ipd.rainbow.ui.activity.store.ProductEvaluateActivity
import com.ipd.rainbow.utils.IndicatorHelper
import com.ipd.rainbow.utils.StoreType
import com.ipd.rainbow.utils.StringUtils
import kotlinx.android.synthetic.main.fragment_product_detail_top.view.*
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
                params.height = (mContentView.store_banner.measuredWidth * 0.6).toInt()
                mContentView.store_banner.requestLayout()

            }
        })
    }

    override fun loadData() {
        val bannerList = ArrayList(StringUtils.splitImages(mProductInfo.PICS).map { BannerBean(it) })
        if (!TextUtils.isEmpty(mProductInfo.VIDEO)) {
            bannerList.add(0, BannerBean(mProductInfo.VIDEO, true, mProductInfo.VIDEO_URL))
        }

        mContentView.store_banner.adapter = BannerPagerAdapter(context, bannerList) { pos, info ->
            PictureAndVideoPreviewActivity.launch(mActivity, bannerList, pos)
        }
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
            }
            else -> {
                mContentView.ll_price.visibility = View.GONE

            }
        }


        mContentView.rl_product_extra.setProductInfo(mProductInfo)
        mContentView.tv_cart_product_name.text = mProductInfo.PROCUCT_NAME
        mContentView.tv_price.text = mProductInfo.PRICE_AREA
        mContentView.tv_old_price.text = "￥${mProductInfo.PRICE}"
//        mContentView.tv_old_price.visibility = if (mProductInfo.KIND == StoreType.PRODUCT_NORMAL) View.GONE else View.VISIBLE


        if (mProductInfo.POST_FEE.toFloat() == 0f) {
            mContentView.tv_express_fee.text = "快递：免运费"
        } else {
            mContentView.tv_express_fee.text = "快递：￥${mProductInfo.POST_FEE}"
        }
        mContentView.tv_sales.text = "月销${mProductInfo.SALE}件"
        mContentView.tv_ship_address.text = mProductInfo.ADDRESS


        //评价
        mContentView.tv_evaluate_num.text = "商品评价（${mProductInfo.ASSESS}）"
        if (mProductInfo.ASSESS_LIST == null || mProductInfo.ASSESS_LIST.isEmpty()) {
            mContentView.evaluate_recycler_view.visibility = View.GONE
        } else {
            mContentView.evaluate_recycler_view.visibility = View.VISIBLE
            val evaluateList = mProductInfo.ASSESS_LIST

            mContentView.evaluate_recycler_view.adapter = ProductDetailEvaluateAdapter(mActivity, evaluateList) {
                ProductEvaluateActivity.launch(mActivity, it.ASSESS_ID)
            }

        }

        mContentView.ll_evaluate_header.setOnClickListener {
            //全部评价
            if (mActivity is ProductDetailActivity) {
                (mActivity as ProductDetailActivity).switchToEvaluate()
            }
        }


    }

    override fun initListener() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mContentView.store_banner.stopAutoScroll()
    }

}